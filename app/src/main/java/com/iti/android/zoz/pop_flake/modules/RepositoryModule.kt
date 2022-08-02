package com.iti.android.zoz.pop_flake.modules

import com.iti.android.zoz.pop_flake.data.repository.IRepository
import com.iti.android.zoz.pop_flake.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideRepository(repository: Repository): IRepository
}