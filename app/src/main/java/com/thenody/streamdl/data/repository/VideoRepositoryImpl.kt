package com.thenody.streamdl.data.repository

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.thenody.streamdl.data.datasource.YoutubeDataSource
import com.thenody.streamdl.domain.model.StreamOption
import com.thenody.streamdl.domain.model.VideoInfo
import com.thenody.streamdl.domain.repository.VideoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val ds: YoutubeDataSource
) : VideoRepository {

    override suspend fun getVideoInfo(url: String): VideoInfo = withContext(Dispatchers.IO) {
        val info = ds.getStreamInfo(url)
        val streams = buildList {
            info.videoStreams.forEach { add(StreamOption("${it.quality} (${it.format})", it.url, false)) }
            info.videoOnlyStreams.forEach {
                val q = it.itagItem?.quality ?: it.resolution
                add(StreamOption("$q (${it.format}) - video only", it.url, false))
            }
            info.audioStreams.forEach { add(StreamOption("${it.averageBitrate / 1000} kbps (${it.format}) - audio", it.url, true)) }
        }
        VideoInfo(info.name, streams)
    }

    override suspend fun downloadVideo(title: String, url: String) {
        val request = DownloadManager.Request(url.toUri())
            .setTitle(title)
            .setDescription("Загрузка видео")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.mp4")
        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
    }
}

