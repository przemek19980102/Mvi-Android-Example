package com.example.appName.feature.sum.calculation

import com.example.appName.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class CalculationModule {

    @Provides
    fun provideCalculationView(fragmen: CalculationFragment): CalculationView = fragmen

    @Provides
    fun provideCalculationPresenter(
            sumView: CalculationView,
            initialState: CalculationViewState
    ): CalculationPresenter = CalculationPresenter(sumView, initialState)

    @Provides
    fun provideInitialSumViewState(fragment: CalculationFragment): CalculationViewState = fragment.savedInstanceState?.getSerializable(
            BaseActivity.KEY_SAVED_ACTIVITY_VIEW_STATE) as? CalculationViewState
            ?: CalculationViewState()
}