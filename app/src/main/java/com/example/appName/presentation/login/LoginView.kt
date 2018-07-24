package com.example.appName.presentation.login

import io.reactivex.Observable

interface LoginView {
    val changeUsernameIntent: Observable<String>
    val changePasswordIntent: Observable<String>
    val loginIntent: Observable<LoginData>
    val registerIntent: Observable<Any>
}
