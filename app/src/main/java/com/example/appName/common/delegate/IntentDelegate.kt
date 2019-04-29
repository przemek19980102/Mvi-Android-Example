package com.example.appName.common.delegate

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class IntentDelegate<LIFECYCLE_OWNER : LifecycleOwner, T>(lifecycleOwner: LIFECYCLE_OWNER, private val provideValueObservable: () -> Observable<T>) : ReadOnlyProperty<LIFECYCLE_OWNER, PublishSubject<T>>, LifecycleObserver {

    private var value: PublishSubject<T> = PublishSubject.create()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun whenLifecycleOwnerReady() {
        provideValueObservable.invoke().subscribe(value)
    }

    override fun getValue(thisRef: LIFECYCLE_OWNER, property: KProperty<*>): PublishSubject<T> {
        return value
    }
}