package com.example.appName.common.di

import com.example.appName.feature.sum.calculation.CalculationFragment
import com.example.appName.feature.sum.calculation.CalculationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [CalculationModule::class])
    abstract fun bindCalculationFragment(): CalculationFragment
}