package com.casper.layoutoverlay.shared.ktx

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.startMainActivity() {
    startActivity(
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("layoutoverlay://home")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )
}