package com.example.appName.feature.login

import com.example.appName.feature.login.validation.PasswordValidator
import com.example.appName.feature.login.validation.UsernameValidator
import java.io.Serializable

data class LoginViewState(val usernameValidationResult: UsernameValidator.ValidationResult = UsernameValidator.ValidationResult.NOT_FILLED,
                          val passwordValidationResult: PasswordValidator.ValidationResult = PasswordValidator.ValidationResult.NOT_FILLED,
                          val loading: Boolean = false,
                          val error: Throwable? = null,
                          val loginSuccess: Boolean = false) : Serializable {
    constructor(previous: LoginViewState,
                usernameValidationResult: UsernameValidator.ValidationResult = previous.usernameValidationResult,
                passwordValidationResult: PasswordValidator.ValidationResult = previous.passwordValidationResult,
                loading: Boolean = false,
                error: Throwable? = null,
                successful: Boolean = false) : this(usernameValidationResult,
            passwordValidationResult,
            loading,
            error,
            successful)
}
