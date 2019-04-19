package com.example.appName.common.repository.user

import com.example.appName.common.data.UserDataSource
import com.example.appName.common.model.domain.User
import io.reactivex.Single

class UserRepository(val userDataSource: UserDataSource) {
    fun login(username: String, password: String): Single<User> {
        return userDataSource.login(username, password)
    }
}