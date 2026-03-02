package com.kolisnichenko2828.itemtracker

import androidx.lifecycle.ViewModel
import com.kolisnichenko2828.itemtracker.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {
    suspend fun getLastViewedId(): Int? {
        return itemsRepository.getLastViewedId().firstOrNull()
    }
}