package com.kolisnichenko2828.itemtracker

class MainContract {
    sealed interface Event {
        object LoadLastViewedItem : Event
    }
}