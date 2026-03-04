package com.kolisnichenko2828.itemtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _itemId = Channel<Int>()
    val itemId: Flow<Int> = _itemId.receiveAsFlow()

    fun setEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.LoadLastViewedItem -> {
                loadLastViewedItem()
            }
        }
    }

    private fun loadLastViewedItem() {
        viewModelScope.launch {
            val lastId = itemsRepository.getLastViewedId().firstOrNull() ?: -1
            _itemId.send(lastId)
        }
    }
}