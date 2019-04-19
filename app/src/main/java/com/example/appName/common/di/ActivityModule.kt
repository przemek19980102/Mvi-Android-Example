package com.example.appName.common.di

import com.example.appName.feature.login.LoginActivity
import com.example.appName.feature.login.di.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity
}