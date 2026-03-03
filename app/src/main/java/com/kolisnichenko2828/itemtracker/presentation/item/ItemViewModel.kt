package com.kolisnichenko2828.itemtracker.presentation.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import com.kolisnichenko2828.itemtracker.domain.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _itemState = MutableStateFlow<Item?>(null)
    val itemState: StateFlow<Item?> = _itemState.asStateFlow()

    fun loadItem(itemId: Int) {
        _itemState.value = itemsRepository.getItemById(itemId)
    }

    fun saveLastViewedId(id: Int) {
        viewModelScope.launch {
            itemsRepository.saveLastViewedId(id)
        }
    }
}