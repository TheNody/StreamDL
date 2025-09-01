package com.thenody.streamdl.domain.model

data class VideoInfo(
    val title: String,
    val streams: List<StreamOption>
)

data class StreamOption(
    val quality: String,
    val url: String?,
    val isAudio: Boolean
)
