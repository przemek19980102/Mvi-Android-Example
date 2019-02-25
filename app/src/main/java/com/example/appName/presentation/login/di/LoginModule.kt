package com.example.appName.presentation.login.di

import android.os.Bundle
import com.example.appName.presentation.login.*
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

const val LOGIN_ACTIVITY_SCOPE = "loginActivityScope"

val loginModule = module {
    scope(LOGIN_ACTIVITY_SCOPE) { (savedInstanceState: Bundle?) ->
        savedInstanceState?.getSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE) as? LoginViewState
                ?: LoginViewState()
    }

    scope(LOGIN_ACTIVITY_SCOPE) { (loginActivity: LoginActivity) ->
        LoginPresenter(loginActivity, get(), get { parametersOf(loginActivity) })
    }
}