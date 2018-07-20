package com.example.appName.presentation.simpleActivity

import com.example.appName.presentation.di.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [SimpleModule::class])
interface SimpleComponent {
    fun inject(fragment: SimpleActivity)
}
