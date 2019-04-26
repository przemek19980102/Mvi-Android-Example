package com.example.appName.feature.sum

import com.example.appName.base.BasePresenter
import io.reactivex.Observable

class SumPresenter(
        private val view: SumView,
        initialState: SumViewState
) : BasePresenter<SumViewState, SumViewState.PartialState>() {
    private var firstNumber: Long = 0L
    private var secondNumber: Long = 0L

    init {
        subscribeToViewIntents(initialState,
                createChangeFirstNumberObservable(),
                createChangeSecondNumberObservable()
        )
    }

    override fun reduceViewState(previousState: SumViewState, partialState: SumViewState.PartialState): SumViewState {
        return when (partialState) {
            is SumViewState.PartialState.SumCalculated -> {
                SumViewState(
                        previousState,
                        firstNumber = firstNumber,
                        secondNumber = secondNumber,
                        sum = partialState.sum
                )
            }
        }
    }

    private fun createChangeFirstNumberObservable(): Observable<SumViewState.PartialState> {
        return view.changeFirstNumberIntent
                .map {
                    firstNumber = it
                    makeSum()
                }
                .map { SumViewState.PartialState.SumCalculated(it) }
    }

    private fun createChangeSecondNumberObservable(): Observable<SumViewState.PartialState> {
        return view.changeSecondNumberIntent
                .map {
                    secondNumber = it
                    makeSum()
                }
                .map { SumViewState.PartialState.SumCalculated(it) }
    }

    private fun makeSum() = firstNumber + secondNumber

}