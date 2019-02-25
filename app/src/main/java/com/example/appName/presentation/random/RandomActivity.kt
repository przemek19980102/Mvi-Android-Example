package com.example.appName.presentation.random

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import com.example.appName.R
import com.example.appName.presentation.di.SINGLE_ACTIVITY_SCOPE
import com.example.appName.presentation.random.di.RANDOM_SCOPE
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_random.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.core.parameter.parametersOf

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

class RandomActivity : AppCompatActivity(), RandomView {
    //region Properties
    private val presenter: RandomPresenter by inject { parametersOf(this@RandomActivity) }

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

        getOrCreateScope(SINGLE_ACTIVITY_SCOPE)
        getOrCreateScope(RANDOM_SCOPE)

        setContentView(R.layout.activity_random)

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

    @VisibleForTesting
    fun subscribeToViewState() {
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
