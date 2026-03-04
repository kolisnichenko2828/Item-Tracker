package com.kolisnichenko2828.itemtracker.presentation.list

import androidx.lifecycle.ViewModel
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListContract.State())
    val uiState: StateFlow<ListContract.State> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        _uiState.update { it.copy(isLoading = true) }

        val itemsList = itemsRepository.getItems()

        _uiState.update {
            it.copy(
                isLoading = false,
                items = itemsList
            )
        }
    }
}