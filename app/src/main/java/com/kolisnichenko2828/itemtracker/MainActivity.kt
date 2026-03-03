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
import com.kolisnichenko2828.itemtracker.presentation.navigation.ItemTrackerApp
import com.kolisnichenko2828.itemtracker.presentation.service.AppForegroundService
import com.kolisnichenko2828.itemtracker.presentation.theme.ItemTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionsAndStartService()
        handleIntent(intent)

        setContent {
            ItemTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ItemTrackerApp(
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }

    private fun requestPermissionsAndStartService() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            startAppService()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startAppService()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        // androd 12+
        if (intent?.action == "ACTION_OPEN_FROM_NOTIFICATION") {
            mainViewModel.loadLastViewedItem()
        } else {
            // android <= 11
            val itemId = intent?.getIntExtra("last_viewed_id", -1) ?: -1
            if (itemId != -1) mainViewModel.requestNavigation(itemId)
        }
    }

    private fun startAppService() {
        val serviceIntent = Intent(this, AppForegroundService::class.java)
        startForegroundService(serviceIntent)
    }
}
