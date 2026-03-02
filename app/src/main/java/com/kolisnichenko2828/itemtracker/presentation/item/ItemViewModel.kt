package com.kolisnichenko2828.itemtracker.presentation.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import com.kolisnichenko2828.itemtracker.domain.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    fun getItem(id: Int): Item? {
        return itemsRepository.getItemById(id)
    }

    fun saveLastViewedId(id: Int) {
        viewModelScope.launch {
            itemsRepository.saveLastViewedId(id)
        }
    }
}