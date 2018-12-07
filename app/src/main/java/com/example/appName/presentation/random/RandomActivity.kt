package com.example.appName.presentation.random

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.appName.R
import com.example.appName.presentation.random.di.DaggerRandomComponent
import com.example.appName.presentation.random.di.RandomModule
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_random.*
import javax.inject.Inject

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

class RandomActivity : AppCompatActivity(), RandomView {
    //region Properties
    @Inject
    lateinit var presenter: RandomPresenter

    private var disposable: Disposable? = null
    //endregion

    //region Intents
    override val rollFirstIntent: Observable<Any>
        get() = RxView.clicks(randomRollFirstNumber)

    override val rollSecondIntent: Observable<Any>
        get() = RxView.clicks(randomRollSecondNumber)
    //endregion

    //region Activity methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_random)

        DaggerRandomComponent.builder()
                .randomModule(RandomModule(this, savedInstanceState))
                .build().inject(this)

        subscribeToViewState()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposable?.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val viewState = presenter.getCurrentViewState()
        outState?.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, viewState)
    }

    private fun subscribeToViewState() {
        val viewStateObservable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)

        disposable = viewStateObservable
    }
    //endregion

    private fun render(viewState: RandomViewState) {
        randomFirstNumber.text = viewState.firstRoll.toString()
        randomSecondNumber.text = viewState.secondRoll.toString()
    }

}
