package com.thenody.streamdl.core.util.extension

import android.net.Uri

fun String.ensureYoutubeAbsolute(): String =
    if (startsWith("http")) this else "https://www.youtube.com$this"

fun String.safeEncode(): String = Uri.encode(this)
fun String.safeDecode(): String = Uri.decode(this)