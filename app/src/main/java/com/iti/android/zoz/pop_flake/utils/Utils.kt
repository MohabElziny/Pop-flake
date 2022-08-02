package com.iti.android.zoz.pop_flake.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String) = Snackbar.make(requireView(), message,
    Snackbar.LENGTH_SHORT).show()