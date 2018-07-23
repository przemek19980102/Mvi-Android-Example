package com.example.appName.presentation.login

import com.example.appName.presentation.base.Presenter
import com.example.appName.domain.UserRepository
import com.example.appName.presentation.login.validation.PasswordValidator
import com.example.appName.presentation.login.validation.UsernameValidator
import com.example.appName.presentation.utils.ApplicationNavigator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val view: LoginView,
                                         private val userRepository: UserRepository,
                                         private val applicationNavigator: ApplicationNavigator,
                                         initialState: LoginViewState) : Presenter<LoginViewState, LoginPartialState>() {

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

    private fun createLoginObservable(): Observable<LoginPartialState> = Observable
            .combineLatest<String, String, Pair<String, String>>(
                    view.changeUsernameIntent,
                    view.changeUsernameIntent,
                    BiFunction { username, password -> Pair(username, password) })
            .sample(view.loginIntent)
            .flatMap { (username, password) ->
                userRepository
                        .login(username, password).toObservable()
                        .map { LoginPartialState.LoginCompleted() as LoginPartialState }
                        .doOnNext { applicationNavigator.goToMainScreen() }
                        .startWith(LoginPartialState.Loading())
                        .onErrorReturn { LoginPartialState.LoginError(it) }
            }

    private fun createRegisterObservable(): Observable<LoginPartialState> = view.registerIntent
            .doOnNext {
                applicationNavigator.apply {
                    goToMainScreen()
                    finishCurrentActivity()
                }
            }
            .map { LoginPartialState.NoOp() }

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
