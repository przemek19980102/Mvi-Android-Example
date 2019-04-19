package com.example.appName.feature.login.validation

import android.content.Context
import com.example.appName.R

fun UsernameValidator.ValidationResult.getMessage(context: Context): String? =
        when (this) {
            UsernameValidator.ValidationResult.BLANK ->
                context.getString(R.string.username_blank_error)
            UsernameValidator.ValidationResult.INVALID ->
                context.getString(R.string.username_invalid_error)
            UsernameValidator.ValidationResult.VALID,
            UsernameValidator.ValidationResult.NOT_FILLED -> null
        }

fun PasswordValidator.ValidationResult.getMessage(context: Context): String? =
        when (this) {
            PasswordValidator.ValidationResult.BLANK ->
                context.getString(R.string.password_blank_error)
            PasswordValidator.ValidationResult.INVALID ->
                context.getString(R.string.password_invalid_error)
            PasswordValidator.ValidationResult.VALID,
            PasswordValidator.ValidationResult.NOT_FILLED -> null
        }
