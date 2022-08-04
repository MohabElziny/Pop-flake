package com.iti.android.zoz.pop_flake.ui.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.android.zoz.pop_flake.databinding.FragmentSettingsBinding
import com.iti.android.zoz.pop_flake.ui.settings.adapter.ComplaintListAdapter
import com.iti.android.zoz.pop_flake.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.buffer

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val args: SettingsFragmentArgs by navArgs()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var _complaintListAdapter: ComplaintListAdapter? = null
    private val complaintListAdapter get() = _complaintListAdapter!!

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
        settingsViewModel.getComplaintList()

        observeOnThemeMode()
        observeOnComplaintList()

        handleSwitchButton()
        handleSubmitButton()

        initializeComplaintListAdapter()

        args.complaint?.let { complaint ->
            settingsViewModel.addComplaint(complaint)
        }
    }

    private fun initializeComplaintListAdapter() {
        _complaintListAdapter = ComplaintListAdapter()
        binding.complaintsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = complaintListAdapter
        }
    }

    private fun observeOnComplaintList() {
        lifecycleScope.launchWhenStarted {
            settingsViewModel.complaints.buffer().collect { complaintList ->
                if (complaintList.isNotEmpty()) {
                    binding.complaintsRecyclerView.visibility = View.VISIBLE
                    complaintListAdapter.setComplaintList(complaintList)
                }
            }
        }
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

    private fun handleSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionNavigationSettingsToComplaintForm()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _complaintListAdapter = null
        _binding = null
    }
}