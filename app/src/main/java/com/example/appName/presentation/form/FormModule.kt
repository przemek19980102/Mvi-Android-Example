package com.example.appName.presentation.form

import dagger.Module
import dagger.Provides

@Module
class FormModule(private val activity: FormActivity) {

    @Provides
    fun provideLoginView(): FormView = activity

    @Provides
    fun provideSavedViewState(): FormViewState =
            activity.intent.getSerializableExtra(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? FormViewState
                    ?: FormViewState()
}
