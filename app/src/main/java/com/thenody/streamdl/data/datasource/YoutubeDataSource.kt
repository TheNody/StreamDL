package com.thenody.streamdl.data.datasource

import com.thenody.streamdl.domain.model.YtKiosk
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.channel.ChannelInfo
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabInfo
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeLiveLinkHandlerFactory
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingGamingVideosLinkHandlerFactory
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingMusicLinkHandlerFactory
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor

class YoutubeDataSource {

    private val service = ServiceList.YouTube

    fun getKioskItems(kiosk: YtKiosk): List<StreamInfoItem> {
        val url = when (kiosk) {
            YtKiosk.TRENDING -> YoutubeTrendingLinkHandlerFactory.INSTANCE
                .getUrl("", emptyList(), "")
            YtKiosk.MUSIC -> YoutubeTrendingMusicLinkHandlerFactory.INSTANCE
                .getUrl("", emptyList(), "")
            YtKiosk.GAMING -> YoutubeTrendingGamingVideosLinkHandlerFactory.INSTANCE
                .getUrl("", emptyList(), "")
            YtKiosk.LIVE -> YoutubeLiveLinkHandlerFactory.INSTANCE
                .getUrl("", emptyList(), "")
        }

        return runCatching {
            val info = KioskInfo.getInfo(service, url)
            info.relatedItems.filterIsInstance<StreamInfoItem>()
        }.getOrElse {
            val query = when (kiosk) {
                YtKiosk.TRENDING -> "trending"
                YtKiosk.MUSIC    -> "music trending"
                YtKiosk.GAMING   -> "gaming trending"
                YtKiosk.LIVE     -> "live"
            }
            val handler = service.searchQHFactory.fromQuery(query)
            val search  = SearchInfo.getInfo(service, handler)
            search.relatedItems.filterIsInstance<StreamInfoItem>()
        }
    }

    fun searchItems(query: String): Pair<List<StreamInfoItem>, SearchInfo> {
        val handler = service.searchQHFactory.fromQuery(query)
        val info = SearchInfo.getInfo(service, handler)
        return info.relatedItems.filterIsInstance<StreamInfoItem>() to info
    }

    fun searchMore(info: SearchInfo, max: Int = 40): List<StreamInfoItem> {
        val handler = service.searchQHFactory.fromQuery(info.searchString)
        val out = mutableListOf<StreamInfoItem>()
        var next = info.nextPage
        while (next != null && out.size < max) {
            val more = SearchInfo.getMoreItems(service, handler, next)
            out += more.items.filterIsInstance<StreamInfoItem>()
            next = more.nextPage
        }
        return out
    }

    fun getStreamInfo(url: String) = StreamInfo.getInfo(service, url)

    fun getChannelInfo(url: String) = ChannelInfo.getInfo(service, url)

    fun getChannelVideosTab(channelInfo: ChannelInfo): List<StreamInfoItem> {
        val tab = channelInfo.tabs.firstOrNull {
            it.contentFilters.isEmpty() || "videos" in it.contentFilters
        } ?: return emptyList()

        val tabInfo = ChannelTabInfo.getInfo(service, tab)
        return tabInfo.relatedItems.filterIsInstance<StreamInfoItem>()
    }

    fun getSuggestions(query: String): List<String> {
        return runCatching {
            val extractor: SuggestionExtractor? = service.suggestionExtractor
            extractor?.suggestionList(query) ?: emptyList()
        }.getOrElse { emptyList() }
    }
}