package com.example.appName.feature.random

import android.os.Bundle
import com.example.appName.R
import com.example.appName.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_random.*

class RandomActivity : BaseActivity<RandomViewState, RandomPresenter>(
        R.layout.activity_random
), RandomView {
    //region intents
    override val rollFirstIntent: Observable<Any>
        get() = RxView.clicks(randomRollFirstNumber)

    override val rollSecondIntent: Observable<Any>
        get() = RxView.clicks(randomRollSecondNumber)
    //endregion

    override fun onViewReady(savedInstanceState: Bundle?) {

    }

    override fun render(viewState: RandomViewState) {
        randomFirstNumber.text = viewState.firstRoll.toString()
        randomSecondNumber.text = viewState.secondRoll.toString()
    }
}
