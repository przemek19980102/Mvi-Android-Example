package com.example.appName.feature.sum

import com.example.appName.R
import com.example.appName.base.BaseActivity
import com.example.appName.common.extension.createTextChangesObservable
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_sum.*

class SumActivity : BaseActivity<SumViewState, SumPresenter>(
        R.layout.activity_sum
), SumView {

    override val changeFirstNumberIntent: Observable<Long>
        get() = firstNumber.createTextChangesObservable().map {
            it.toLong()
        }

    override val changeSecondNumberIntent: Observable<Long>
        get() = secondNumber.createTextChangesObservable().map {
            it.toLong()
        }

    override fun render(viewState: SumViewState) {
        sum.text = viewState.sum.toString()
    }
}