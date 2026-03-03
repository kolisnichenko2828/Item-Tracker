package com.kolisnichenko2828.itemtracker.presentation.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.kolisnichenko2828.itemtracker.MainViewModel
import com.kolisnichenko2828.itemtracker.presentation.item.ItemScreen
import com.kolisnichenko2828.itemtracker.presentation.list.ListScreen
import kotlinx.parcelize.Parcelize

sealed interface Screen : Parcelable {
    @Parcelize
    object List : Screen
    @Parcelize
    data class Item(val itemId: Int) : Screen
}

@Composable
fun ItemTrackerApp(
    mainViewModel: MainViewModel
) {
    val backStack = rememberSaveable { mutableStateListOf<Screen>(Screen.List) }

    LaunchedEffect(Unit) {
        mainViewModel.itemId.collect { id ->
            if (backStack.lastOrNull() != Screen.Item(id)) {
                backStack.add(Screen.Item(id))
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { screen ->
            when (screen) {
                is Screen.List -> NavEntry(screen) {
                    ListScreen(
                        onItemClick = { id ->
                            backStack.add(Screen.Item(id))
                        }
                    )
                }
                is Screen.Item -> NavEntry(screen) {
                    ItemScreen(
                        itemId = screen.itemId
                    )
                }
            }
        }
    )
}