package com.example.appName.feature.random

import com.example.appName.base.BaseView
import io.reactivex.subjects.PublishSubject

interface RandomView : BaseView {
    val rollFirstIntent: PublishSubject<Any>
    val rollSecondIntent: PublishSubject<Any>
}
