package com.example.appName.feature.login.di

import com.example.appName.common.repository.user.UserRepository
import com.example.appName.feature.login.LoginActivity
import com.example.appName.feature.login.LoginPresenter
import com.example.appName.feature.login.LoginView
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideLoginView(activity: LoginActivity): LoginView = activity

    @Provides
    fun provideLoginPresenter(activity: LoginActivity, userRepository: UserRepository): LoginPresenter = LoginPresenter(activity, userRepository)

//    @Provides
//    fun provideSavedViewState(activity: LoginActivity): LoginViewState =
//            activity.savedInstanceState?.getSerializable(
//                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? LoginViewState
//                    ?: LoginViewState()
}
