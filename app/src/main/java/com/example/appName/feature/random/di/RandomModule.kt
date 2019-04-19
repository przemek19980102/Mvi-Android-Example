package com.example.appName.feature.random.di

import android.os.Bundle
import com.example.appName.feature.random.KEY_SAVED_ACTIVITY_VIEW_STATE
import com.example.appName.feature.random.RandomActivity
import com.example.appName.feature.random.RandomView
import com.example.appName.feature.random.RandomViewState
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class RandomModule(private val activity: RandomActivity,
                   private val savedInstanceState: Bundle?) {

    @Provides
    fun provideLoginView(): RandomView = activity

    @Provides
    fun provideSavedViewState(): RandomViewState =
            savedInstanceState?.getSerializable(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? RandomViewState
                    ?: RandomViewState()

    @Provides
    fun provideRandomNumberGenerator(): Random = Random()
}
