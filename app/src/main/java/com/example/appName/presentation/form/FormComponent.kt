package com.example.appName.presentation.form

import com.example.appName.presentation.di.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [FormModule::class])
interface FormComponent {
    fun inject(fragment: FormActivity)
}
