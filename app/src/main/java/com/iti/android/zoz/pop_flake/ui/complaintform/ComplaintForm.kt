package com.iti.android.zoz.pop_flake.ui.complaintform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.pojos.Complaint
import com.iti.android.zoz.pop_flake.databinding.FragmentComplaintFormBinding
import com.iti.android.zoz.pop_flake.utils.showSnackBar
import java.util.*

class ComplaintForm : Fragment() {

    private var _complaintFormBinding: FragmentComplaintFormBinding? = null
    private val complaintFormBinding get() = _complaintFormBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _complaintFormBinding =
            FragmentComplaintFormBinding.inflate(layoutInflater, container, false)
        return complaintFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complaintFormBinding.apply {
            btnSubmit.setOnClickListener {
                submitFormProgressBar.visibility = View.VISIBLE
                hiddenImage.visibility = View.VISIBLE
                preparePojo()
            }
        }
    }

    private fun preparePojo() {
        val complaintMessage = complaintFormBinding.edtComplaint.text.toString().trim()
        if (complaintMessage.isNotEmpty()) {
            handleSuccessPreparation(complaintMessage)
        } else {
            handleFailedPreparation()
        }
    }

    private fun handleSuccessPreparation(complaintMessage: String) {
        findNavController().navigate(
            ComplaintFormDirections.actionComplaintFormToNavigationSettings(
                Complaint(
                    Calendar.getInstance().timeInMillis,
                    complaintFormBinding.edtFirstName.text.toString().trim(),
                    complaintFormBinding.edtLastName.text.toString().trim(),
                    complaintFormBinding.edtEmail.text.toString().trim(),
                    complaintMessage
                )
            )
        )
    }

    private fun handleFailedPreparation() {
        showSnackBar(getString(R.string.complaint_message))
        complaintFormBinding.apply {
            submitFormProgressBar.visibility = View.GONE
            hiddenImage.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _complaintFormBinding = null
    }

}