package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import com.example.appName.BuildConfig
import com.example.appName.presentation.di.SINGLE_ACTIVITY_SCOPE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import java.io.Serializable

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>> : AppCompatActivity() {
    private var disposable: Disposable? = null

    abstract val presenter: PRESENTER

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
        super.onCreate(savedInstanceState)

        bindScope(getOrCreateScope(SINGLE_ACTIVITY_SCOPE))

        if (!BuildConfig.DEBUG) {
            window
                    .setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                            WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposable?.dispose()
    }

    abstract fun render(viewState: VIEW_STATE)
}