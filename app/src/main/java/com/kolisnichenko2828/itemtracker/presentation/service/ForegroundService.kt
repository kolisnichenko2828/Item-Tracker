package com.kolisnichenko2828.itemtracker.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kolisnichenko2828.itemtracker.MainActivity
import com.kolisnichenko2828.itemtracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForegroundService : Service() {
    private val channelId = "item_tracker_channel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val context = this
        val requestCode = 0
        val activityFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingAction = "ACTION_OPEN_LAST_VIEWED"

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Для Android 12 и выше прямой запуск MainActivity
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.addFlags(activityFlags)
            activityIntent.action = pendingAction
            PendingIntent.getActivity(context, requestCode, activityIntent, pendingFlags)
        } else {
            // Для Android 11 и ниже оставляем BroadcastReceiver
            val broadcastIntent = Intent(this, BroadcastReceiver::class.java)
            PendingIntent.getBroadcast(context, requestCode, broadcastIntent, pendingFlags)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle("App Service is running")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Item Tracker Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?) = null
}