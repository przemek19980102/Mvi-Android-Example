package com.example.appName.domain

import io.reactivex.Single

interface UserRepository {
    fun login(username: String, password: String): Single<User>
}