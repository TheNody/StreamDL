package com.thenody.streamdl.data.mapper

import com.thenody.streamdl.domain.model.ChannelData
import org.schabi.newpipe.extractor.channel.ChannelInfo

fun ChannelInfo.toChannelHeader(subscribersText: String?): ChannelData =
    ChannelData(
        title = name ?: "Channel",
        url = url,
        avatarUrl = avatars.firstOrNull()?.url,
        subscribersText = subscribersText,
        description = description,
        videos = emptyList()
    )
