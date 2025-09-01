package com.thenody.streamdl.domain.model

data class ChannelData(
    val title: String,
    val url: String,
    val avatarUrl: String?,
    val subscribersText: String?,
    val description: String?,
    val videos: List<VideoListItem>
)