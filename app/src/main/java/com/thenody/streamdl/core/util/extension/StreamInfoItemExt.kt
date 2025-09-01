package com.thenody.streamdl.core.util.extension

import org.schabi.newpipe.extractor.stream.StreamInfoItem

fun StreamInfoItem.avatarUrlOrNull(): String? =
    this.uploaderAvatars.firstOrNull()?.url
