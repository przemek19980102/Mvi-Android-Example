package com.example.appName.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.MenuItem
import android.view.WindowManager
import com.example.appName.BuildConfig
import com.example.appName.R
import com.example.appName.common.extension.goToMainScreen
import com.example.appName.common.extension.goToRegisterActivity
import dagger.android.DaggerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>>(@LayoutRes layoutId: Int) : DaggerActivity(), BaseView {
    private var disposable: Disposable? = null

    @Inject
    lateinit var presenter: PRESENTER

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

    override fun onCreate(savedInstanceState: Bundle?) {
        bindView()
        super.onCreate(savedInstanceState)
        subscribeToViewState()
        onViewReady()

        if (!BuildConfig.DEBUG) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposable?.dispose()
    }

    fun bindView() {
        setContentView(R.layout.activity_login)
    }

    abstract fun onViewReady()

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
}