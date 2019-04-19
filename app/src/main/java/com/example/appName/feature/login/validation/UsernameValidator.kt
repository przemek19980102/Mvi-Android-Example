package com.example.appName.feature.login.validation

object UsernameValidator {
    fun validate(username: String): ValidationResult =
            when {
                username.isBlank() -> ValidationResult.BLANK
                username.length < 6 -> ValidationResult.INVALID
                else -> ValidationResult.VALID
            }


    enum class ValidationResult {
        NOT_FILLED, BLANK, INVALID, VALID
    }
}