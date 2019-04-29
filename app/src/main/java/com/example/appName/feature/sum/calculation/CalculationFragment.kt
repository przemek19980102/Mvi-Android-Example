package com.example.appName.feature.sum.calculation

import com.example.appName.R
import com.example.appName.base.BaseFragment
import com.example.appName.common.delegate.IntentDelegate
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_calculation.*
import java.util.*

class CalculationFragment : BaseFragment<CalculationViewState, CalculationPresenter>(
        R.layout.fragment_calculation
), CalculationView {

    override val changeFirstNumberIntent: PublishSubject<Long> by IntentDelegate(this) {
        firstNumber.textChanges().map { it.toString().toLongOrNull() ?: 0 }
    }

    val observable = Observable()

    override val changeSecondNumberIntent: PublishSubject<Long> by IntentDelegate(this) {
        secondNumber.textChanges().map { it.toString().toLongOrNull() ?: 0 }
    }

    override fun render(viewState: CalculationViewState) {
        sum.text = viewState.sum.toString()
    }

    companion object {
        fun newInstance(): CalculationFragment = CalculationFragment()
    }
}