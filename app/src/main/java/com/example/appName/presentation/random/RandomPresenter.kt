package com.example.appName.presentation.random

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
        TODO("Create observables and subscribe to view intents")
    }

    private fun subscribeToViewIntents(initialState: RandomViewState,
                                       vararg observables: Observable<RandomPartialState>) {
        viewIntentsDisposable = Observable
                .merge(observables.asList())
                .scan(initialState, this::reduceViewState)
                .subscribe(stateSubject::onNext)
    }

    private fun reduceViewState(previousState: RandomViewState, partialState: RandomPartialState): RandomViewState =
            TODO("Implement reduce method")

    //region Lifecycle methods
    fun getStateObservable() = stateSubject as Observable<RandomViewState>

    fun getCurrentViewState(): RandomViewState? = stateSubject.value

    fun dispose() = viewIntentsDisposable.dispose()
    //endregion
}
