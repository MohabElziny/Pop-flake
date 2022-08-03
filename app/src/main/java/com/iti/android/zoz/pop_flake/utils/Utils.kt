package com.iti.android.zoz.pop_flake.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun Fragment.showSnackBar(message: String) = Snackbar.make(
    requireView(), message,
    Snackbar.LENGTH_SHORT
).show()

fun TextInputEditText.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")

    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            query.value = p0.toString().trim()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
    return query
}