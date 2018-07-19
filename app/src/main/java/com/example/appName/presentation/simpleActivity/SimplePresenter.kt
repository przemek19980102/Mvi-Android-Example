package com.example.appName.presentation.simpleActivity

import com.example.appName.presentation.base.Presenter
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class SimplePresenter @Inject constructor(private val view: SimpleView,
                                          private val random: Random,
                                          initialState: SimpleViewState) : Presenter<SimpleViewState, SimplePartialState>() {

    init {
        subscribeToViewIntents(initialState,
                createRollFirstObservable(),
                createRollSecondObservable())
    }

    private fun createRollFirstObservable(): Observable<SimplePartialState> =
            view.rollFirstIntent
                    .map { generateRandomNumber() }
                    .map { SimplePartialState.FirstRolled(it) }

    private fun createRollSecondObservable(): Observable<SimplePartialState> =
            view.rollSecondIntent
                    .map { generateRandomNumber() }
                    .map { SimplePartialState.SecondRolled(it) }

    private fun generateRandomNumber() = (random.nextInt().and(Integer.MAX_VALUE)) % 6 + 1

    override fun reduceViewState(previousState: SimpleViewState, partialState: SimplePartialState) =
            when (partialState) {
                is SimplePartialState.FirstRolled ->
                    SimpleViewState(previousState, firstRoll = partialState.generatedNumber)
                is SimplePartialState.SecondRolled ->
                    SimpleViewState(previousState, secondRoll = partialState.generatedNumber)
            }
}
