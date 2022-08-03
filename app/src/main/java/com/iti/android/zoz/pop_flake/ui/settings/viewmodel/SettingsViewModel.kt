package com.iti.android.zoz.pop_flake.ui.settings.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.android.zoz.pop_flake.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private val _themeMode: MutableLiveData<Int> = MutableLiveData()
    val themeMode: LiveData<Int> get() = _themeMode

    fun switchOff() {
        setTheme(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun switchOn() {
        setTheme(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun setTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        repository.setThemeMode(themeMode)
    }

    fun setSwitchMode() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = async { repository.getThemeMode() }
            _themeMode.postValue(value.await())
        }
    }

}