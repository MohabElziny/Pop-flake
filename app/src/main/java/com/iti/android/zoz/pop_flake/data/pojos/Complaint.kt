package com.iti.android.zoz.pop_flake.data.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Complaint(
    val dateTime: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val complaint: String,
) : Parcelable
