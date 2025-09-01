package com.thenody.streamdl.app

import android.app.Application
import com.thenody.streamdl.core.network.OkHttpDownloader
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class StreamDLApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val downloader = OkHttpDownloader(client)
        NewPipe.init(downloader)
    }
}
