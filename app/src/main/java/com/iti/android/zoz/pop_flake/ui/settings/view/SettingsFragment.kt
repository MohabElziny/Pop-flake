package com.iti.android.zoz.pop_flake.ui.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.iti.android.zoz.pop_flake.databinding.FragmentSettingsBinding
import com.iti.android.zoz.pop_flake.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel.setSwitchMode()
        observeOnThemeMode()

        handleSwitchButton()
    }

    private fun observeOnThemeMode() {
        settingsViewModel.themeMode.observe(viewLifecycleOwner) { themeMode ->
            when (themeMode) {
                AppCompatDelegate.MODE_NIGHT_NO -> binding.darkModeSwitch.isChecked = false
                AppCompatDelegate.MODE_NIGHT_YES -> binding.darkModeSwitch.isChecked = true
                else -> binding.darkModeSwitch.isChecked = false
            }
        }
    }

    private fun handleSwitchButton() {
        binding.darkModeSwitch.setOnClickListener {
            if (binding.darkModeSwitch.isChecked)
                settingsViewModel.switchOn()
            else
                settingsViewModel.switchOff()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}