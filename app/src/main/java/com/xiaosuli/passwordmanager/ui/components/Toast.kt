package com.xiaosuli.passwordmanager.ui.components

import android.content.Context
import android.widget.Toast
import com.xiaosuli.passwordmanager.App

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun showToast(
    text: String,
    context: Context = App.context,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, text, duration).show()
}