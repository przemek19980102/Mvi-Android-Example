package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import com.example.appName.BuildConfig
import com.example.appName.presentation.di.ActivityModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>> : AppCompatActivity() {
    private var disposable: Disposable? = null

    @Suppress("LeakingThis")
    val activityModule: ActivityModule = ActivityModule(this)

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

    abstract fun render(viewState: VIEW_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}