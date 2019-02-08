package com.example.appName.presentation.login

import com.example.appName.presentation.base.BasePresenter
import com.example.appName.domain.UserRepository
import com.example.appName.presentation.login.validation.PasswordValidator
import com.example.appName.presentation.login.validation.UsernameValidator
import com.example.appName.presentation.utils.ApplicationNavigator
import io.reactivex.Observable

class LoginPresenter constructor(private val view: LoginView,
                                         private val userRepository: UserRepository,
                                         private val applicationNavigator: ApplicationNavigator,
                                         initialState: LoginViewState) : BasePresenter<LoginViewState, LoginPartialState>() {

    //region Intent methods
    init {
        subscribeToViewIntents(initialState,
                createChangeUsernameObservable(),
                createChangePasswordObservable(),
                createRegisterObservable(),
                createLoginObservable())
    }

    private fun createChangeUsernameObservable(): Observable<LoginPartialState> =
            view.changeUsernameIntent
                    .map { UsernameValidator.validate(it) }
                    .map { LoginPartialState.UsernameValidated(it) as LoginPartialState }

    private fun createChangePasswordObservable(): Observable<LoginPartialState> =
            view.changePasswordIntent
                    .map { PasswordValidator.validate(it) }
                    .map { LoginPartialState.PasswordValidated(it) as LoginPartialState }

    private fun createLoginObservable(): Observable<LoginPartialState> = view.loginIntent
            .flatMap { loginData ->
                userRepository
                        .login(loginData.username, loginData.password).toObservable()
                        .map { LoginPartialState.LoginCompleted() as LoginPartialState }
                        .doOnNext {
                            applicationNavigator.apply {
                                goToMainScreen()
                                finishCurrentActivity()
                            }
                        }
                        .startWith(LoginPartialState.Loading())
                        .onErrorReturn { LoginPartialState.LoginError(it) }
            }

    private fun createRegisterObservable(): Observable<LoginPartialState> = view.registerIntent
            .doOnNext { applicationNavigator.apply { goToRegisterActivity() } }
            .map { LoginPartialState.NoOp() }

    //endregion

    override fun reduceViewState(previousState: LoginViewState, partialState: LoginPartialState) =
            when (partialState) {
                is LoginPartialState.UsernameValidated ->
                    LoginViewState(
                            previousState,
                            usernameValidationResult = partialState.usernameValidationResult)
                is LoginPartialState.PasswordValidated ->
                    LoginViewState(
                            previousState,
                            passwordValidationResult = partialState.passwordValidationResult)
                is LoginPartialState.Loading -> LoginViewState(previousState,
                        loading = true)
                is LoginPartialState.LoginError ->
                    LoginViewState(
                            previousState,
                            error = partialState.throwable)
                is LoginPartialState.LoginCompleted ->
                    LoginViewState(
                            previousState,
                            successful = true)
                is LoginPartialState.NoOp -> previousState
            }
}
