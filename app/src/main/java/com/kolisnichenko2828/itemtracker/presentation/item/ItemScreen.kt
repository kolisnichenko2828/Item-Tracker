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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.itemtracker.R
import com.kolisnichenko2828.itemtracker.domain.Item

@Composable
fun ItemScreen(
    itemId: Int,
    itemViewModel: ItemViewModel = hiltViewModel()
) {
    val uiState by itemViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(itemId) {
        itemViewModel.setEvent(ItemContract.Event.LoadItem(itemId))
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        when (val currentItem = uiState.item) {
            is Item -> {
                Text(
                    text = stringResource(R.string.item_id, currentItem.id),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.item_name, currentItem.name),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.item_description, currentItem.description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {
                Text(text = stringResource(R.string.item_not_found))
            }
        }
    }
}