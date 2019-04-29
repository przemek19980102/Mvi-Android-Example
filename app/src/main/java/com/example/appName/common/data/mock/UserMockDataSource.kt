package com.example.appName.common.data.mock

import android.app.Application
import com.example.appName.common.data.UserDataSource
import com.example.appName.common.model.domain.User
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserMockDataSource @Inject constructor(private val application: Application) : UserDataSource {
    override fun login(username: String, password: String): Single<User> =
            if (username == "test@test.com" && password == "Password123")
                Single.just(resultUser).delay(200, TimeUnit.MILLISECONDS)
            else
                Single.error(Exception("Invalid username or password ${application.applicationInfo}"))

    companion object {
        val resultUser = User("1")
    }
}