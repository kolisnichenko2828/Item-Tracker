package com.kolisnichenko2828.itemtracker.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val actionView = "com.kolisnichenko2828.itemtracker.ACTION_OPENLASTVIEWEDITEM"
        val actionUri = "app://task.one".toUri()

        if (intent?.action == actionView) {
            val activityFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val activityIntent = Intent(Intent.ACTION_VIEW, actionUri)
            activityIntent.addFlags(activityFlags)
            context.startActivity(activityIntent)
        }
    }

}