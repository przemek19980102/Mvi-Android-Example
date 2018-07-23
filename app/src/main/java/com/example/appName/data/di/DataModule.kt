package com.example.appName.data.di

import com.example.appName.domain.UserRepository
import com.example.appName.data.user.MockUserRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideUserRepository(): UserRepository = MockUserRepository()
}