package com.kolisnichenko2828.itemtracker.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kolisnichenko2828.itemtracker.MainActivity
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject lateinit var itemsRepository: ItemsRepository

    override fun onReceive(context: Context, intent: Intent?) {
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lastId = itemsRepository.getLastViewedId().firstOrNull()

                val targetIntent = Intent(
                    context,
                    MainActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    if (lastId != null) {
                        putExtra("last_viewed_id", lastId)
                    }
                }
                context.startActivity(targetIntent)
            } finally {
                pendingResult.finish()
            }
        }
    }

}