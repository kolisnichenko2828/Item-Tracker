package com.kolisnichenko2828.itemtracker.presentation.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemContract.State(item = null))
    val uiState: StateFlow<ItemContract.State> = _uiState.asStateFlow()

    fun setEvent(event: ItemContract.Event) {
        when (event) {
            is ItemContract.Event.LoadItem -> {
                loadItem(event.itemId)
            }
        }
    }

    private fun loadItem(itemId: Int) {
        viewModelScope.launch {
            val data = itemsRepository.getItemById(itemId)
            itemsRepository.saveLastViewedId(itemId)
            _uiState.update { it.copy(item = data) }
        }
    }
}