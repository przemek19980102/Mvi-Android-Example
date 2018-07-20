package com.example.appName.presentation.form

import com.example.appName.presentation.base.Presenter
import javax.inject.Inject

class FormPresenter @Inject constructor(view: FormView,
                                        initialState: FormViewState) : Presenter<FormViewState, FormPartialState>() {

    init {
        TODO("Subscribe to view intents")

        subscribeToViewIntents(initialState)
    }

    override fun reduceViewState(previousState: FormViewState, partialState: FormPartialState) =
            TODO()
}
