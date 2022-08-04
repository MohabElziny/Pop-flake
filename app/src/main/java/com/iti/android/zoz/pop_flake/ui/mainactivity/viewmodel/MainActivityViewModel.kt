package com.iti.android.zoz.pop_flake.ui.mainactivity.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    fun setThemeMode() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = async { repository.getThemeMode() }
            setTheme(value.await())
        }
    }

    private fun setTheme(themeMode: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            AppCompatDelegate.setDefaultNightMode(themeMode)
        }
    }
}