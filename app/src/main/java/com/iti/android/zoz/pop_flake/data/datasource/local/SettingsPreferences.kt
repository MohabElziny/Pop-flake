package com.iti.android.zoz.pop_flake.data.datasource.local

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.iti.android.zoz.pop_flake.utils.THEME_KEY
import javax.inject.Inject

class SettingsPreferences @Inject constructor(private val preferences: SharedPreferences) {
    fun getThemeMode() = preferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO)

    fun setThemeMode(themeMode: Int) {
        val editor = preferences.edit()
        editor.putInt(THEME_KEY, themeMode)
        editor.apply()
    }

}