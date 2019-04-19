package com.example.appName.feature.login

import android.os.Bundle
import com.example.appName.R
import com.example.appName.base.BaseActivity
import com.example.appName.common.extension.createTextChangesObservable
import com.example.appName.common.extension.getMessage
import com.example.appName.common.inputValidation.PasswordValidator
import com.example.appName.common.inputValidation.UsernameValidator
import com.example.appName.common.inputValidation.getMessage
import com.example.appName.common.model.request.LoginRequest
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert

class LoginActivity : BaseActivity<LoginViewState, LoginPresenter>(
        R.layout.activity_login
), LoginView {
    //region Intents
    override val changeUsernameIntent: Observable<String>
        get() = loginUsername.createTextChangesObservable()

    override val changePasswordIntent: Observable<String>
        get() = loginPassword.createTextChangesObservable()

    override val loginIntent: Observable<LoginRequest>
        get() = RxView.clicks(loginButton).map {
            val username = loginUsername.text.toString()
            val password = loginPassword.text.toString()
            LoginRequest(username, password)
        }

    override val registerIntent: Observable<Any>
        get() = RxView.clicks(loginRegisterButton)
    //endregion

    override fun onViewReady(savedInstanceState: Bundle?) {

    }

    //region Render methods
    override fun render(viewState: LoginViewState) {
        if (viewState.loginSuccess) {
            return
        }

        renderLoading(viewState)

        renderValidationResults(viewState)

        loginButton.isEnabled = allInputsAreValid(viewState)

    }

    private fun renderLoading(viewState: LoginViewState) {
        if (viewState.loading) {
            showChildIfNotShownAlready(LOADING_VIEW_INDEX)
        } else {
            showChildIfNotShownAlready(MAIN_VIEW_INDEX)
        }
    }

    private fun renderValidationResults(viewState: LoginViewState) {
        loginUsername.error = viewState.usernameValidationResult.getMessage(this)
        loginPassword.error = viewState.passwordValidationResult.getMessage(this)

        if (viewState.error != null) {
            alert { message = (viewState.error.getMessage(this@LoginActivity)) }.show()
        }
    }

    private fun showChildIfNotShownAlready(childIndex: Int) {
        loginLoadingSwitcher.apply {
            if (displayedChild != childIndex)
                displayedChild = childIndex
        }
    }

    private fun allInputsAreValid(viewState: LoginViewState) =
            (viewState.usernameValidationResult == UsernameValidator.ValidationResult.VALID &&
                    viewState.passwordValidationResult == PasswordValidator.ValidationResult.VALID)
    //endregion

    companion object {
        private const val MAIN_VIEW_INDEX = 0
        private const val LOADING_VIEW_INDEX = 1
    }
}
