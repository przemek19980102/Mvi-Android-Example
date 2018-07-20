package com.example.appName.presentation.simpleActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.appName.R
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple.*
import javax.inject.Inject

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

class SimpleActivity : AppCompatActivity(), SimpleView {
    //region Private methods
    @Inject
    lateinit var presenter: SimplePresenter

    private val disposables: CompositeDisposable = CompositeDisposable()
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

        setContentView(R.layout.activity_simple)

        DaggerSimpleComponent.builder()
                .simpleModule(SimpleModule(this))
                .build().inject(this)

        subscribeToViewState()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposables.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val viewState = presenter.getCurrentViewState()
        outState?.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, viewState)
    }

    private fun subscribeToViewState() {
        val viewStateObservable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)

        disposables.add(viewStateObservable)
    }
    //endregion

    private fun render(viewState: SimpleViewState) {
        randomFirstNumber.text = viewState.firstRoll.toString()
        randomSecondNumber.text = viewState.secondRoll.toString()
    }

}
