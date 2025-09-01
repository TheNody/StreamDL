package com.thenody.streamdl.core.di

import com.thenody.streamdl.data.repository.VideoRepositoryImpl
import com.thenody.streamdl.data.repository.YoutubeRepositoryImpl
import com.thenody.streamdl.domain.repository.VideoRepository
import com.thenody.streamdl.domain.repository.YoutubeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVideoRepository(
        impl: VideoRepositoryImpl
    ): VideoRepository

    @Binds
    @Singleton
    abstract fun bindYoutubeRepository(
        impl: YoutubeRepositoryImpl
    ): YoutubeRepository
}
