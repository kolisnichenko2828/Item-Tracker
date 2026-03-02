package com.kolisnichenko2828.itemtracker

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.kolisnichenko2828.itemtracker.presentation.navigation.ItemTrackerApp
import com.kolisnichenko2828.itemtracker.presentation.service.AppForegroundService
import com.kolisnichenko2828.itemtracker.presentation.theme.ItemTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        startAppService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startAppService()
        }

        Intent(this, AppForegroundService::class.java).also { serviceIntent ->
            startForegroundService(serviceIntent)
        }

        if (intent?.action == "ACTION_OPEN_FROM_NOTIFICATION") {
            lifecycleScope.launch {
                val lastId = viewModel.getLastViewedId()
                if (lastId != null) {
                    intent.putExtra("last_viewed_id", lastId)
                }
                setupCompose()
            }
        } else {
            setupCompose()
        }
    }

    private fun setupCompose() {
        setContent {
            ItemTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ItemTrackerApp(initialIntent = intent)
                    }
                }
            }
        }
    }

    private fun startAppService() {
        Intent(this, AppForegroundService::class.java).also { serviceIntent ->
            startService(serviceIntent)
        }
    }
}
