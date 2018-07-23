package com.example.appName.presentation.form.di

import com.example.appName.presentation.form.FormActivity
import com.example.appName.presentation.form.FormView
import com.example.appName.presentation.form.FormViewState
import com.example.appName.presentation.form.KEY_SAVED_ACTIVITY_VIEW_STATE
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
