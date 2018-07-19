package com.example.appName.presentation.simpleActivity

import android.os.Bundle
import com.example.appName.R
import com.example.appName.presentation.base.BaseActivity
import com.example.appName.presentation.di.ActivityModule
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : BaseActivity<SimpleViewState, SimplePresenter>(), SimpleView {
    override val rollFirstIntent: Observable<Any>
        get() = RxView.clicks(randomRollFirstNumber)

    override val rollSecondIntent: Observable<Any>
        get() = RxView.clicks(randomRollSecondNumber)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simple)

        DaggerSimpleComponent.builder()
                .activityModule(ActivityModule(this))
                .simpleModule(SimpleModule(this))
                .build().inject(this)

        subscribeToViewState()
    }

    override fun render(viewState: SimpleViewState) {
        randomFirstNumber.text = viewState.firstRoll.toString()
        randomSecondNumber.text = viewState.secondRoll.toString()
    }
}
