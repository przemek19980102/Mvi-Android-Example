package com.example.appName.presentation.login.di

import com.example.appName.data.di.DataModule
import com.example.appName.presentation.di.ActivityModule
import com.example.appName.presentation.di.ActivityScope
import com.example.appName.presentation.login.LoginActivity
import dagger.Component

@ActivityScope
@Component(modules = [LoginModule::class, ActivityModule::class, DataModule::class])
interface LoginComponent {
    fun inject(fragment: LoginActivity)
}
