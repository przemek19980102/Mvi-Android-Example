package com.example.appName.feature.login

import com.example.appName.base.BaseActivity.Companion.KEY_SAVED_ACTIVITY_VIEW_STATE
import com.example.appName.common.data.repository.user.UserRepository
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideLoginView(activity: LoginActivity): LoginView = activity

    @Provides
    fun provideLoginPresenter(
            loginView: LoginView,
            userRepository: UserRepository,
            initialState: LoginViewState
    ): LoginPresenter = LoginPresenter(loginView, userRepository, initialState)

    @Provides
    fun provideInitialLoginViewState(activity: LoginActivity): LoginViewState = activity.savedInstanceState?.getSerializable(
            KEY_SAVED_ACTIVITY_VIEW_STATE) as? LoginViewState
            ?: LoginViewState()
}
