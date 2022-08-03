package com.iti.android.zoz.pop_flake.data.datasource.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val settingsPreferences: SettingsPreferences) :
    ILocalDataSource {

    override fun getThemeMode(): Int = settingsPreferences.getThemeMode()

    override fun setThemeMode(themeMode: Int) = settingsPreferences.setThemeMode(themeMode)
}