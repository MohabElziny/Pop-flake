package com.iti.android.zoz.pop_flake.data.datasource.local

interface ILocalDataSource {

    fun getThemeMode(): Int

    fun setThemeMode(themeMode: Int)
}