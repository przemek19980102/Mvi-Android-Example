package com.example.appName.presentation.login

import com.example.appName.presentation.login.validation.PasswordValidator
import com.example.appName.presentation.login.validation.UsernameValidator

sealed class LoginPartialState {
    data class UsernameValidated(val usernameValidationResult: UsernameValidator.ValidationResult) : LoginPartialState()
    data class PasswordValidated(val passwordValidationResult: PasswordValidator.ValidationResult) : LoginPartialState()
    data class LoginError(val throwable: Throwable) : LoginPartialState()
    class LoginCompleted : LoginPartialState()
    class Loading : LoginPartialState()
    class NoOp : LoginPartialState()
}
