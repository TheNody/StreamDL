package com.thenody.streamdl.domain.model

data class VideoListItem(
    val title: String,
    val thumbnailUrl: String,
    val channel: String,
    val url: String,
    val channelAvatarUrl: String? = null,
    val channelUrl: String? = null,
    val durationSeconds: Long? = null,
    val viewCount: Long? = null,
    val publishedText: String? = null
)