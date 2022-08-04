package com.iti.android.zoz.pop_flake.ui.settings.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.pojos.Complaint
import com.iti.android.zoz.pop_flake.databinding.ComplaintCardBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ComplaintListAdapter : RecyclerView.Adapter<ComplaintListAdapter.ComplaintListViewHolder>() {
    private var complaintList: List<Complaint> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setComplaintList(complaintList: List<Complaint>) {
        this.complaintList = complaintList
        notifyDataSetChanged()
    }

    inner class ComplaintListViewHolder(private val complaintCardBinding: ComplaintCardBinding) :
        RecyclerView.ViewHolder(complaintCardBinding.root) {
        private val complaint get() = complaintList[adapterPosition]

        fun bindData() {
            setName()
            complaintCardBinding.txtMessage.text = complaint.complaint
            setTime()
            setEmail()
        }

        private fun setTime() {
            complaintCardBinding.txtTimeDate.text =
                convertLongToDayDate(complaint.dateTime).plus(" ").plus(
                    convertLongToTime(complaint.dateTime)
                )
        }

        private fun setEmail() {
            if (complaint.email.isEmpty())
                complaintCardBinding.txtEmail.visibility = View.GONE
            else
                complaintCardBinding.txtEmail.text = complaint.email
        }

        private fun setName() {
            complaintCardBinding.txtName.text =
                if (complaint.firstName.isEmpty() && complaint.lastName.isEmpty()) {
                    complaintCardBinding.root.context.getString(R.string.name).plus(
                        complaintCardBinding.root.context.getString(
                            R.string.anonymous
                        )
                    )
                } else {
                    complaintCardBinding.root.context.getString(R.string.name)
                        .plus(complaint.firstName).plus(" ").plus(complaint.lastName)
                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintListViewHolder =
        ComplaintListViewHolder(
            ComplaintCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ComplaintListViewHolder, position: Int) =
        holder.bindData()

    override fun getItemCount(): Int = complaintList.size

    fun convertLongToDayDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("d MMM, yyyy", Locale("en"))
        return format.format(date)
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(TimeUnit.SECONDS.toMillis(time))
        val format = SimpleDateFormat("h:mm a", Locale("en"))
        return format.format(date)
    }
}