package com.example.appName.feature.login

import com.example.appName.base.BaseView
import com.example.appName.common.model.request.LoginRequest
import io.reactivex.Observable

interface LoginView : BaseView {
    val changeUsernameIntent: Observable<String>
    val changePasswordIntent: Observable<String>
    val loginIntent: Observable<LoginRequest>
    val registerIntent: Observable<Any>
}
