package com.thenody.streamdl.domain.repository

import com.thenody.streamdl.domain.model.ChannelData
import com.thenody.streamdl.domain.model.VideoListItem
import com.thenody.streamdl.domain.model.YtKiosk

interface YoutubeRepository {
    suspend fun getKiosk(kiosk: YtKiosk): List<VideoListItem>
    suspend fun search(query: String): List<VideoListItem>
    suspend fun getChannel(url: String): ChannelData
    suspend fun getSuggestions(query: String): List<String>
}
