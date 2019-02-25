package com.example.appName.presentation.random.di

import com.example.appName.presentation.random.RandomActivity
import com.example.appName.presentation.random.RandomPresenter
import com.example.appName.presentation.random.RandomPresenterImpl
import org.koin.dsl.module.module
import java.util.*

const val RANDOM_SCOPE = "randomScope"

val randomModule = module {
    factory { Random() }

    scope<RandomPresenter>(RANDOM_SCOPE) { (randomActivity: RandomActivity) -> RandomPresenterImpl(randomActivity, get()) }
}
