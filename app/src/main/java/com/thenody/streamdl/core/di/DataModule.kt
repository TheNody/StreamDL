package com.thenody.streamdl.core.di

import com.thenody.streamdl.data.datasource.YoutubeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideYoutubeDataSource(): YoutubeDataSource = YoutubeDataSource()
}