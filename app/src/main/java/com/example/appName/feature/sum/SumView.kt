package com.example.appName.feature.sum

import com.example.appName.base.BaseView
import io.reactivex.Observable

interface SumView : BaseView {
    val changeFirstNumberIntent: Observable<Long>
    val changeSecondNumberIntent: Observable<Long>
}