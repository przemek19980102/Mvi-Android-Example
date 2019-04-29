package com.example.appName.feature.sum

import com.example.appName.base.BaseView
import io.reactivex.subjects.PublishSubject

interface SumView : BaseView {
    val changeFirstNumberIntent: PublishSubject<Long>
    val changeSecondNumberIntent: PublishSubject<Long>
}