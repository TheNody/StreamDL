package com.thenody.streamdl.domain.repository

import com.thenody.streamdl.domain.model.VideoInfo

interface VideoRepository {
    suspend fun getVideoInfo(url: String): VideoInfo
    suspend fun downloadVideo(title: String, url: String)
}
