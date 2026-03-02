package com.kolisnichenko2828.itemtracker.presentation.list

import androidx.lifecycle.ViewModel
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    itemsRepository: ItemsRepository
) : ViewModel() {
    val uiState: StateFlow<ListUiState> = MutableStateFlow(
        value = ListUiState.Content(
            items = itemsRepository.getItems()
        )
    )
}