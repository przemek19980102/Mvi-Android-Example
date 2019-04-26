package com.example.appName.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.MenuItem
import android.view.WindowManager
import com.example.appName.BuildConfig
import com.example.appName.common.extension.goToMainScreen
import com.example.appName.common.extension.goToRegisterActivity
import dagger.android.DaggerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>>(@LayoutRes val layoutId: Int) : DaggerActivity(), BaseView {

    @Inject
    lateinit var presenter: PRESENTER
    var savedInstanceState: Bundle? = null


    private var disposable: Disposable? = null

    protected fun subscribeToViewState() {
        disposable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        inflateView()
        super.onCreate(savedInstanceState)
        subscribeToViewState()

        if (!BuildConfig.DEBUG) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val viewState = presenter.getCurrentViewState()
        outState?.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, viewState)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposable?.dispose()
    }

    private fun inflateView() {
        setContentView(layoutId)
    }

    abstract fun render(viewState: VIEW_STATE)

    override fun finishCurrentFeature() {
        finish()
    }

    override fun goToRegisterFeature() {
        goToRegisterActivity()
    }

    override fun goToMainFeature() {
        goToMainScreen()
    }

    companion object {
        const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"
    }
}