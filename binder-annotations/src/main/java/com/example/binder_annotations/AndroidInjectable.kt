package com.example.binder_annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class AndroidInjectable(val module: KClass<*>)