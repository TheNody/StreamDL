package com.thenody.streamdl.data.mapper

import com.thenody.streamdl.domain.model.VideoListItem
import org.schabi.newpipe.extractor.stream.StreamInfoItem

fun StreamInfoItem.toVideoListItem(): VideoListItem =
    VideoListItem(
        title = name ?: "Без названия",
        thumbnailUrl = thumbnails.firstOrNull()?.url.orEmpty(),
        channel = uploaderName ?: "Unknown",
        url = url,
        channelAvatarUrl = uploaderAvatars.firstOrNull()?.url,
        channelUrl = uploaderUrl,
        durationSeconds = duration,
        viewCount = viewCount,
        publishedText = textualUploadDate
    )
