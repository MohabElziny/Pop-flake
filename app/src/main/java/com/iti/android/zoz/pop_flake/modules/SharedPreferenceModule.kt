package com.iti.android.zoz.pop_flake.modules

import android.content.Context
import android.content.SharedPreferences
import com.iti.android.zoz.pop_flake.data.datasource.local.SettingsPreferences
import com.iti.android.zoz.pop_flake.utils.PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )

    @Provides
    fun provideSettingsPreferences(preferences: SharedPreferences): SettingsPreferences =
        SettingsPreferences(preferences)
}