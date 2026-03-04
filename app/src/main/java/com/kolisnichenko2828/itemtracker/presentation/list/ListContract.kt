package com.kolisnichenko2828.itemtracker.presentation.list

import com.kolisnichenko2828.itemtracker.domain.Item

class ListContract {
    data class State(
        val isLoading: Boolean = false,
        val items: List<Item> = emptyList()
    )
}