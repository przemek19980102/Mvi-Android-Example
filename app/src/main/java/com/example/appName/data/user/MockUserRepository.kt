package com.example.appName.data.user

import com.example.appName.domain.User
import com.example.appName.domain.UserRepository
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MockUserRepository : UserRepository {
    companion object {
        val resultUser = User("1")
    }

    override fun login(username: String, password: String): Single<User> =
            if (username == "test@test.com" && password == "Password123")
                Single.just(resultUser).delay(200, TimeUnit.MILLISECONDS)
            else
                Single.error(Exception("Invalid username or password"))

}