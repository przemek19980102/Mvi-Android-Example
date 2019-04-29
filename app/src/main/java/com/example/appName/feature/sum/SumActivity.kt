package com.example.appName.feature.sum

import android.os.Bundle
import com.example.appName.R
import com.example.appName.base.BaseActivity
import com.example.appName.common.delegate.IntentDelegate
import com.example.appName.feature.sum.calculation.CalculationFragment
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_sum.*

class SumActivity : BaseActivity<SumViewState, SumPresenter>(
        R.layout.activity_sum
), SumView {

    override val changeFirstNumberIntent: PublishSubject<Long> by IntentDelegate(this) {
        firstNumber.textChanges().map { it.toString().toLongOrNull() ?: 0 }
    }

    override val changeSecondNumberIntent: PublishSubject<Long> by IntentDelegate(this) {
        secondNumber.textChanges().map { it.toString().toLongOrNull() ?: 0 }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().apply {
            replace(fragmentFrame.id, CalculationFragment.newInstance())
            commit()
        }
    }

    override fun render(viewState: SumViewState) {
        sum.text = viewState.sum.toString()
    }
}