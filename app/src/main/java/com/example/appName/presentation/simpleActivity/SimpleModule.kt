package com.example.appName.presentation.simpleActivity

import com.example.appName.presentation.base.KEY_SAVED_ACTIVITY_VIEW_STATE
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class SimpleModule(private val activity: SimpleActivity) {

    @Provides
    fun provideLoginView(): SimpleView = activity

    @Provides
    fun provideSavedViewState(): SimpleViewState =
            activity.intent.getSerializableExtra(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? SimpleViewState
                    ?: SimpleViewState()

    @Provides
    fun provideRandomNumberGenerator() : Random = Random()
}
