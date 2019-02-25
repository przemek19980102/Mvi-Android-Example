package com.example.appName

import android.app.Application
import com.example.appName.data.di.dataModule
import com.example.appName.presentation.di.activityModule
import com.example.appName.presentation.login.di.loginModule
import com.example.appName.presentation.random.di.randomModule
import org.koin.android.ext.android.startKoin

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(activityModule, loginModule, dataModule, randomModule))
    }
}