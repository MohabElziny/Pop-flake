package com.iti.android.zoz.pop_flake.modules

import com.iti.android.zoz.pop_flake.data.datasource.remote.NetworkService
import com.iti.android.zoz.pop_flake.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(BASE_URL)
        }.build()

    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

}