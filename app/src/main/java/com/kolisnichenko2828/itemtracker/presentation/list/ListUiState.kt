package com.kolisnichenko2828.itemtracker.presentation.list

import com.kolisnichenko2828.itemtracker.domain.Item

sealed interface ListUiState {
    object Loading : ListUiState
    data class Content(val items: List<Item>) : ListUiState
}