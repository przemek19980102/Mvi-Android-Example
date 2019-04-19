package com.example.appName.common.repository.user

import android.app.Application
import com.example.appName.common.data.UserDataSource
import com.example.appName.common.data.mock.UserMockDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserRepositoryModule {

    @Provides
    @Named(USER_MOCK_DATA_SOURCE)
    fun provideUserMockDataSource(application: Application): UserDataSource = UserMockDataSource(application)

    @Provides
    fun provideUserRepository(@Named(USER_MOCK_DATA_SOURCE) dataSource: UserDataSource): UserRepository = UserRepository(dataSource)

    companion object {
        const val USER_MOCK_DATA_SOURCE = "userMockDataSource"
    }
}