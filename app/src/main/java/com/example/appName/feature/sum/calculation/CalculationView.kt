package com.example.appName.feature.sum.calculation

import com.example.appName.base.BaseView
import io.reactivex.subjects.PublishSubject

interface CalculationView : BaseView {
    val changeFirstNumberIntent: PublishSubject<Long>
    val changeSecondNumberIntent: PublishSubject<Long>
}