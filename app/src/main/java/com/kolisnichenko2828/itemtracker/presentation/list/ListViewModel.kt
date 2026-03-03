package com.kolisnichenko2828.itemtracker.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val itemsRepository: ItemsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListContract.State())
    val uiState: StateFlow<ListContract.State> = _uiState.asStateFlow()

    private val _effect = Channel<ListContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadItems()
    }

    fun setEvent(event: ListContract.Event) {
        when (event) {
            is ListContract.Event.OnItemClicked -> {
                viewModelScope.launch {
                    _effect.send(ListContract.Effect.NavigateToItem(event.itemId))
                }
            }
        }
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