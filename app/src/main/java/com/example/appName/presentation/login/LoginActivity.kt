package com.example.appName.presentation.login

import android.os.Bundle
import com.example.appName.R
import com.example.appName.presentation.base.BaseActivity
import com.example.appName.presentation.login.di.LOGIN_ACTIVITY_SCOPE
import com.example.appName.presentation.login.validation.PasswordValidator
import com.example.appName.presentation.login.validation.UsernameValidator
import com.example.appName.presentation.login.validation.getMessage
import com.example.appName.presentation.utils.createTextChangesObservable
import com.example.appName.presentation.utils.getMessage
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.koin.android.ext.android.get
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.core.parameter.parametersOf

private const val MAIN_VIEW_INDEX = 0
private const val LOADING_VIEW_INDEX = 1

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

class LoginActivity : BaseActivity<LoginViewState, LoginPresenter>(), LoginView {
    //region Intents
    override val changeUsernameIntent: Observable<String>
        get() = loginUsername.createTextChangesObservable()

    override val changePasswordIntent: Observable<String>
        get() = loginPassword.createTextChangesObservable()

    override val loginIntent: Observable<LoginData>
        get() = RxView.clicks(loginButton).map {
            val username = loginUsername.text.toString()
            val password = loginPassword.text.toString()
            LoginData(username, password)
        }

    override val registerIntent: Observable<Any>
        get() = RxView.clicks(loginRegisterButton)
    //endregion

    //region Activity methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getOrCreateScope(LOGIN_ACTIVITY_SCOPE)

        setContentView(R.layout.activity_login)

        presenter = get { parametersOf(this@LoginActivity, savedInstanceState) }

        subscribeToViewState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, presenter.getCurrentViewState())
    }
    //endregion

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
}
