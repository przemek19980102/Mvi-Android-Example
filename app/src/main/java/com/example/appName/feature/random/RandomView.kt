package com.example.appName.feature.random

import com.example.appName.base.BaseView
import io.reactivex.Observable

interface RandomView : BaseView {
    val rollFirstIntent: Observable<Any>
    val rollSecondIntent: Observable<Any>
}
