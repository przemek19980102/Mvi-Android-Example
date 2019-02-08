package com.example.appName

import android.app.Application
import com.example.appName.data.di.dataModule
import com.example.appName.presentation.di.activityModule
import com.example.appName.presentation.login.di.loginModule
import com.example.appName.presentation.random.di.randomModule
import org.koin.standalone.StandAloneContext.startKoin

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(listOf(activityModule, loginModule, dataModule, randomModule))
    }
}