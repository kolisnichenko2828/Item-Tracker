package com.kolisnichenko2828.itemtracker.presentation.item

import com.kolisnichenko2828.itemtracker.domain.Item

class ItemContract {
    data class State(
        val item: Item? = null
    )

    sealed interface Event {
        data class LoadItem(val itemId: Int) : Event
    }
}