package com.example.appName.presentation.di

import android.app.Activity
import com.example.appName.presentation.utils.ApplicationNavigator
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

const val SINGLE_ACTIVITY_SCOPE = "activityScope"

val activityModule: Module = module {
    scope(SINGLE_ACTIVITY_SCOPE) { (activity: Activity) -> ApplicationNavigator(activity) }
}