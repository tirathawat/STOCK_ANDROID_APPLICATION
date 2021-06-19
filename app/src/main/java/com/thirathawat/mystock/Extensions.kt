package com.thirathawat.mystock

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.convertToBath() : String {
    return "$this Bath"
}