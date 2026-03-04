package com.kolisnichenko2828.itemtracker.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListContract.State())
    val uiState: StateFlow<ListContract.State> = _uiState
        .onStart {
            if (_uiState.value.items.isEmpty()) {
                loadItems()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ListContract.State()
        )

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