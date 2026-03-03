package com.kolisnichenko2828.itemtracker.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ListScreen(
    onItemClick: (Int) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ListContract.Effect.NavigateToItem -> onItemClick(effect.itemId)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.items, key = { it.id }) { item ->
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setEvent(ListContract.Event.OnItemClicked(item.id))
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}