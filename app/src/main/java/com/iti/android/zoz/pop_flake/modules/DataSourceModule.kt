package com.iti.android.zoz.pop_flake.modules

import com.iti.android.zoz.pop_flake.data.datasource.local.ILocalDataSource
import com.iti.android.zoz.pop_flake.data.datasource.local.LocalDataSource
import com.iti.android.zoz.pop_flake.data.datasource.remote.IRemoteDataSource
import com.iti.android.zoz.pop_flake.data.datasource.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    fun provideLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource
}