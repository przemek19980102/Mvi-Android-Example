package com.example.appName.data.di

import com.example.appName.data.user.MockUserRepository
import com.example.appName.domain.UserRepository
import org.koin.dsl.module.module

val dataModule = module {
    single<UserRepository> { MockUserRepository() }
}