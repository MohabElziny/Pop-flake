package com.iti.android.zoz.pop_flake.modules

import com.iti.android.zoz.pop_flake.data.remotedatasource.IRemoteDataSource
import com.iti.android.zoz.pop_flake.data.remotedatasource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}