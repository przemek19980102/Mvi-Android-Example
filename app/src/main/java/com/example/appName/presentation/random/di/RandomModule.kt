package com.example.appName.presentation.random.di

import android.os.Bundle
import com.example.appName.presentation.random.*
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import java.util.*

const val RANDOM_SCOPE = "randomScope"

val randomModule = module {
    factory { Random() }

    factory { (savedInstanceState: Bundle?) ->
        savedInstanceState?.getSerializable(
                KEY_SAVED_ACTIVITY_VIEW_STATE) as? RandomViewState
                ?: RandomViewState()
    }

    scope<RandomPresenter>(RANDOM_SCOPE) { (randomActivity: RandomActivity, savedInstanceState: Bundle?) -> RandomPresenterImpl(randomActivity, get(), get { parametersOf(savedInstanceState) }) }
}
