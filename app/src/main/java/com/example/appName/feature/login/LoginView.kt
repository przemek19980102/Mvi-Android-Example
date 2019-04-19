package com.example.appName.feature.login

import com.example.appName.base.BaseView
import io.reactivex.Observable

interface LoginView : BaseView {
    val changeUsernameIntent: Observable<String>
    val changePasswordIntent: Observable<String>
    val loginIntent: Observable<LoginData>
    val registerIntent: Observable<Any>
}
