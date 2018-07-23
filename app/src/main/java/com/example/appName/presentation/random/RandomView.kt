package com.example.appName.presentation.random

import io.reactivex.Observable

interface RandomView {
    val rollFirstIntent : Observable<Any>
    val rollSecondIntent : Observable<Any>
}
