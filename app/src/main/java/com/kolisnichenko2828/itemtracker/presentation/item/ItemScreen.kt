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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ItemScreen(
    itemId: Int,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val item = viewModel.getItem(itemId)

    LaunchedEffect(itemId) {
        viewModel.saveLastViewedId(itemId)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        if (item != null) {
            Text(
                text = "ID: ${item.id}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Name: ${item.name}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${item.description}",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(text = "Item not found")
        }
    }
}