package com.iti.android.zoz.pop_flake.utils.modules

import com.iti.android.zoz.pop_flake.domain.IPostersUseCase
import com.iti.android.zoz.pop_flake.domain.PostersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindsPosterUseCase(postersUseCase: PostersUseCase): IPostersUseCase
}