package com.example.appName.common.di

import com.example.appName.feature.login.LoginActivity
import com.example.appName.feature.login.LoginModule
import com.example.appName.feature.random.RandomActivity
import com.example.appName.feature.random.RandomModule
import com.example.appName.feature.sum.SumActivity
import com.example.appName.feature.sum.SumModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [RandomModule::class])
    abstract fun bindRandomActivity(): RandomActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SumModule::class])
    abstract fun bindSumActivity(): SumActivity
}