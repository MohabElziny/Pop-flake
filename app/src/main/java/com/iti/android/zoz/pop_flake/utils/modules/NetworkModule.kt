package com.iti.android.zoz.pop_flake.utils.modules

import com.iti.android.zoz.pop_flake.data.datasource.remote.NetworkService
import com.iti.android.zoz.pop_flake.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            readTimeout(15, TimeUnit.SECONDS)
            connectTimeout(15, TimeUnit.SECONDS)
        }.build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
            baseUrl(BASE_URL)
        }.build()

    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)


}