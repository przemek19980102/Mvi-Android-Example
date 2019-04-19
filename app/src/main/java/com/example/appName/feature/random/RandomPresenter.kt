package com.example.appName.feature.random

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class RandomPresenter @Inject constructor(private val view: RandomView,
                                          private val random: Random,
                                          initialState: RandomViewState) {
    private val stateSubject: BehaviorSubject<RandomViewState> = BehaviorSubject.create()
    private lateinit var viewIntentsDisposable: Disposable

    init {
        subscribeToViewIntents(initialState, createRollFirstObservable(),
                createRollSecondObservable())
    }

    private fun createRollFirstObservable(): Observable<RandomPartialState> {
        return view.rollFirstIntent
                .map { generateRandomNumber() }
                .map { RandomPartialState.FirstRolled(it) }
    }

    private fun createRollSecondObservable(): Observable<RandomPartialState> {
        return view.rollSecondIntent
                .map { generateRandomNumber() }
                .map { RandomPartialState.SecondRolled(it) }
    }

    private fun generateRandomNumber() = (random.nextInt().and(Integer.MAX_VALUE)) % 6 + 1

    private fun subscribeToViewIntents(initialState: RandomViewState,
                                       vararg observables: Observable<RandomPartialState>) {
        viewIntentsDisposable = Observable
                .merge(observables.asList())
                .scan(initialState, this::reduceViewState)
                .subscribe(stateSubject::onNext)
    }

    private fun reduceViewState(previousState: RandomViewState, partialState: RandomPartialState) =
            when (partialState) {
                is RandomPartialState.FirstRolled ->
                    RandomViewState(previousState, firstRoll = partialState.generatedNumber)
                is RandomPartialState.SecondRolled ->
                    RandomViewState(previousState, secondRoll = partialState.generatedNumber)
            }

    //region Lifecycle methods
    fun getStateObservable() = stateSubject as Observable<RandomViewState>

    fun getCurrentViewState(): RandomViewState? = stateSubject.value

    fun dispose() = viewIntentsDisposable.dispose()
    //endregion
}
