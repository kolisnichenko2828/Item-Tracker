package com.kolisnichenko2828.itemtracker.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.kolisnichenko2828.itemtracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForegroundService : Service() {
    private val channelId = "item_tracker_channel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val requestCode = 0
        val pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val actionUri = "app://task.one".toUri()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // android 12+
            val activityIntent = Intent(Intent.ACTION_VIEW, actionUri)
            val activityFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            activityIntent.addFlags(activityFlags)

            PendingIntent.getActivity(
                this,
                requestCode,
                activityIntent,
                pendingFlags
            )
        } else {
            // android <= 11
            val broadcastAction = "com.kolisnichenko2828.itemtracker.ACTION_OPENLASTVIEWEDITEM"
            val broadcastIntent = Intent(broadcastAction)
            broadcastIntent.setPackage(this.packageName)

            PendingIntent.getBroadcast(
                this,
                requestCode,
                broadcastIntent,
                pendingFlags
            )
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