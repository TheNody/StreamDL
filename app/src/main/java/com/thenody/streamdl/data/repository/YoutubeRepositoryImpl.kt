package com.thenody.streamdl.data.repository

import com.thenody.streamdl.data.datasource.YoutubeDataSource
import com.thenody.streamdl.data.mapper.toChannelHeader
import com.thenody.streamdl.data.mapper.toVideoListItem
import com.thenody.streamdl.domain.model.ChannelData
import com.thenody.streamdl.domain.model.VideoListItem
import com.thenody.streamdl.domain.model.YtKiosk
import com.thenody.streamdl.domain.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class YoutubeRepositoryImpl @Inject constructor(
    private val ds: YoutubeDataSource
) : YoutubeRepository {

    override suspend fun getKiosk(kiosk: YtKiosk): List<VideoListItem> = withContext(Dispatchers.IO) {
        ds.getKioskItems(kiosk).map { it.toVideoListItem() }
    }

    override suspend fun search(query: String): List<VideoListItem> = withContext(Dispatchers.IO) {
        val (firstPage, info) = ds.searchItems(query)
        val more = ds.searchMore(info, max = 40 - firstPage.size)
        (firstPage + more).map { it.toVideoListItem() }
    }

    override suspend fun getChannel(url: String): ChannelData = withContext(Dispatchers.IO) {
        val info = ds.getChannelInfo(url)
        val subsText = when {
            info.subscriberCount == -1L        -> null
            info.subscriberCount >= 1_000_000  -> String.format("%.1fM подписчиков", info.subscriberCount / 1_000_000.0)
            info.subscriberCount >= 1_000      -> String.format("%.1fK подписчиков", info.subscriberCount / 1_000.0)
            else                               -> "${info.subscriberCount} подписчиков"
        }
        val header = info.toChannelHeader(subsText)
        val videos = ds.getChannelVideosTab(info).map { it.toVideoListItem() }
        header.copy(videos = videos)
    }

    override suspend fun getSuggestions(query: String): List<String> =
        withContext(Dispatchers.IO) { ds.getSuggestions(query) }
}
