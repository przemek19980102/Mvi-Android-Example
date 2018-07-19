package com.example.appName.presentation.simpleActivity

import io.reactivex.Observable

interface SimpleView {
    val rollFirstIntent : Observable<Any>
    val rollSecondIntent : Observable<Any>
}
