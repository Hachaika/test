package com.eltex.androidschool.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.toast(@StringRes res: Int, short: Boolean = true) {
    Toast.makeText(this, res, if (short) {Toast.LENGTH_SHORT} else {Toast.LENGTH_LONG}).show()
}