package com.example.appName.presentation.form.di

import com.example.appName.presentation.di.ActivityScope
import com.example.appName.presentation.form.FormActivity
import dagger.Component

@ActivityScope
@Component(modules = [FormModule::class])
interface FormComponent {
    fun inject(fragment: FormActivity)
}
