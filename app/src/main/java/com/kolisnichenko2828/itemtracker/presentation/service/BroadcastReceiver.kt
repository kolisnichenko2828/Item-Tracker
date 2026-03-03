package com.kolisnichenko2828.itemtracker.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kolisnichenko2828.itemtracker.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "ACTION_OPEN_LAST_VIEWED") {
            val activityFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val activityAction = "ACTION_OPEN_LAST_VIEWED"
            val activityIntent = Intent(context, MainActivity::class.java)
            activityIntent.addFlags(activityFlags)
            activityIntent.action = activityAction
            context.startActivity(activityIntent)
        }
    }

}