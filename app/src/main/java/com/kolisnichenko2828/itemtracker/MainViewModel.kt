package com.kolisnichenko2828.itemtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _itemId = MutableStateFlow<Int?>(null)
    val itemId: StateFlow<Int?> = _itemId.asStateFlow()

    // android 12+
    fun loadLastViewedItem() {
        viewModelScope.launch {
            val lastId = itemsRepository.getLastViewedId().firstOrNull() ?: -1
            _itemId.value = lastId
        }
    }

    // android <= 11
    fun requestNavigation(itemId: Int) {
        _itemId.value = itemId
    }

    fun onNavigationConsumed() {
        _itemId.value = null
    }
}