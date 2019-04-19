package com.example.appName.feature.random

import io.reactivex.Observable

interface RandomView {
    val rollFirstIntent: Observable<Any>
    val rollSecondIntent: Observable<Any>
}
