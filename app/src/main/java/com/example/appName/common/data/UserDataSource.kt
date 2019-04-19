package com.example.appName.common.data

import com.example.appName.domain.User
import io.reactivex.Single

interface UserDataSource {
    fun login(username: String, password: String): Single<User>
}