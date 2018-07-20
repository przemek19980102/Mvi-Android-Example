package com.example.appName.presentation.simpleActivity

import com.example.appName.presentation.base.Presenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class SimplePresenter @Inject constructor(private val view: SimpleView,
                                          private val random: Random,
                                          initialState: SimpleViewState) {
    private val stateSubject: BehaviorSubject<SimpleViewState> = BehaviorSubject.create()
    private lateinit var viewIntentsDisposable: Disposable

    init {
        subscribeToViewIntents(initialState, createRollFirstObservable(),
                createRollSecondObservable())
    }

    private fun createRollFirstObservable(): Observable<SimplePartialState> {
        return view.rollFirstIntent
                .map { generateRandomNumber() }
                .map { SimplePartialState.FirstRolled(it) }
    }

    private fun createRollSecondObservable(): Observable<SimplePartialState> {
        return view.rollSecondIntent
                .map { generateRandomNumber() }
                .map { SimplePartialState.SecondRolled(it) }
    }

    private fun generateRandomNumber() = (random.nextInt().and(Integer.MAX_VALUE)) % 6 + 1

    private fun subscribeToViewIntents(initialState: SimpleViewState,
                                       vararg observables: Observable<SimplePartialState>) {
        viewIntentsDisposable = Observable
                .merge(observables.asList())
                .scan(initialState, this::reduceViewState)
                .subscribe(stateSubject::onNext)
    }

    private fun reduceViewState(previousState: SimpleViewState, partialState: SimplePartialState) =
            when (partialState) {
                is SimplePartialState.FirstRolled ->
                    SimpleViewState(previousState, firstRoll = partialState.generatedNumber)
                is SimplePartialState.SecondRolled ->
                    SimpleViewState(previousState, secondRoll = partialState.generatedNumber)
            }

    //region Lifecycle methods
    fun getStateObservable() = stateSubject as Observable<SimpleViewState>

    fun getCurrentViewState(): SimpleViewState? = stateSubject.value

    fun dispose() = viewIntentsDisposable.dispose()
    //endregion
}
