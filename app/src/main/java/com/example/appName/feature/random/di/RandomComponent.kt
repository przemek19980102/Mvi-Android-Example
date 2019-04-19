package com.example.appName.feature.random.di

import com.example.appName.common.di.ActivityScope
import com.example.appName.feature.random.RandomActivity
import dagger.Component

@ActivityScope
@Component(modules = [RandomModule::class])
interface RandomComponent {
    fun inject(fragment: RandomActivity)
}
