package com.example.appName.presentation.utils

import android.app.Activity
import com.example.appName.presentation.random.RandomActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ApplicationNavigator(private val activity: Activity) {
    fun goToMainScreen() {
        activity.startActivity<RandomActivity>()
    }

    fun goToRegisterActivity() {
        activity.toast("Open register activity")
    }

    fun finishCurrentActivity() {
        activity.finish()
    }
}