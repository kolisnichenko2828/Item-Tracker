package com.kolisnichenko2828.itemtracker.presentation.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.itemtracker.domain.Item

@Composable
fun ItemScreen(
    itemId: Int,
    itemViewModel: ItemViewModel = hiltViewModel()
) {
    val item by itemViewModel.itemState.collectAsStateWithLifecycle()

    LaunchedEffect(itemId) {
        itemViewModel.loadItem(itemId)
        itemViewModel.saveLastViewedId(itemId)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        when (val currentItem = item) {
            is Item -> {
                Text(
                    text = "ID: ${currentItem.id}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Name: ${currentItem.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${currentItem.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {
                Text(text = "Item not found")
            }
        }
    }
}