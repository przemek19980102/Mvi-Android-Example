package com.example.appName.presentation.form

import android.os.Bundle
import com.example.appName.R
import com.example.appName.presentation.base.BaseActivity
import com.example.appName.presentation.form.di.FormModule

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "savedActivityViewState"

class FormActivity : BaseActivity<FormViewState, FormPresenter>(), FormView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form)

        DaggerFormComponent.builder()
                .formModule(FormModule(this)).build().inject(this)

        subscribeToViewState()
    }

    override fun render(viewState: FormViewState) = TODO("Implement rendering")
}
