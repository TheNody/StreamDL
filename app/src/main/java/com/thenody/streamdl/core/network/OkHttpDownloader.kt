package com.thenody.streamdl.core.network

import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException
import java.io.IOException

class OkHttpDownloader(
    private val client: OkHttpClient
) : Downloader() {

    companion object {
        const val USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:140.0) Gecko/20100101 Firefox/140.0"
    }

    @Throws(IOException::class, ReCaptchaException::class)
    override fun execute(request: Request): Response {
        val requestBody = request.dataToSend()?.toRequestBody(null)

        val builder = okhttp3.Request.Builder()
            .method(request.httpMethod(), requestBody)
            .url(request.url())
            .addHeader("User-Agent", USER_AGENT)

        request.headers().forEach { (key, values) ->
            builder.removeHeader(key)
            values.forEach { value -> builder.addHeader(key, value) }
        }

        client.newCall(builder.build()).execute().use { response ->
            if (response.code == 429) {
                throw ReCaptchaException("reCaptcha Challenge requested", request.url())
            }

            val bodyString = response.body?.string()
            val latestUrl = response.request.url.toString()

            return Response(
                response.code,
                response.message,
                response.headers.toMultimap(),
                bodyString,
                latestUrl
            )
        }
    }
}
