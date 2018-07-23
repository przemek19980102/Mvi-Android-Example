package com.example.appName.presentation.login.di

import android.os.Bundle
import com.example.appName.presentation.login.KEY_SAVED_ACTIVITY_VIEW_STATE
import com.example.appName.presentation.login.LoginActivity
import com.example.appName.presentation.login.LoginView
import com.example.appName.presentation.login.LoginViewState
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val activity: LoginActivity,
                  private val savedInstanceState: Bundle?) {

    @Provides
    fun provideLoginView(): LoginView = activity

    @Provides
    fun provideSavedViewState(): LoginViewState =
            savedInstanceState?.getSerializable(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? LoginViewState
                    ?: LoginViewState()
}
