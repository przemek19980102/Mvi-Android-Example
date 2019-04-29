package com.example.appName.feature.random

import com.example.appName.R
import com.example.appName.base.BaseActivity
import com.example.appName.common.delegate.IntentDelegate
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_random.*

class RandomActivity : BaseActivity<RandomViewState, RandomPresenter>(
        R.layout.activity_random
), RandomView {
    //region intents
    override val rollFirstIntent: PublishSubject<Any> by IntentDelegate(this) {
        RxView.clicks(randomRollFirstNumber)
    }

    override val rollSecondIntent: PublishSubject<Any> by IntentDelegate(this) {
        RxView.clicks(randomRollSecondNumber)
    }
    //endregion

    override fun render(viewState: RandomViewState) {
        randomFirstNumber.text = viewState.firstRoll.toString()
        randomSecondNumber.text = viewState.secondRoll.toString()
    }
}
