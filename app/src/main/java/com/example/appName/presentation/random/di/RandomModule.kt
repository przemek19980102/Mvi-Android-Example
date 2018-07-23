package com.example.appName.presentation.random.di

import com.example.appName.presentation.random.KEY_SAVED_ACTIVITY_VIEW_STATE
import com.example.appName.presentation.random.RandomActivity
import com.example.appName.presentation.random.RandomView
import com.example.appName.presentation.random.RandomViewState
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class RandomModule(private val activity: RandomActivity) {

    @Provides
    fun provideLoginView(): RandomView = activity

    @Provides
    fun provideSavedViewState(): RandomViewState =
            activity.intent.getSerializableExtra(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? RandomViewState
                    ?: RandomViewState()

    @Provides
    fun provideRandomNumberGenerator() : Random = Random()
}
