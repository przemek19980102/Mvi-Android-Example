package com.example.appName.presentation.random.di

import com.example.appName.presentation.di.ActivityScope
import com.example.appName.presentation.random.RandomActivity
import dagger.Component

@ActivityScope
@Component(modules = [RandomModule::class])
interface RandomComponent {
    fun inject(fragment: RandomActivity)
}
