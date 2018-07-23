package com.example.appName.presentation.utils

import android.app.Activity
import com.example.appName.presentation.random.RandomActivity
import org.jetbrains.anko.startActivity

class ApplicationNavigator(private val activity: Activity) {
    fun goToMainScreen() {
        activity.startActivity<RandomActivity>()
    }

    fun finishCurrentActivity() {
        activity.finish()
    }
}