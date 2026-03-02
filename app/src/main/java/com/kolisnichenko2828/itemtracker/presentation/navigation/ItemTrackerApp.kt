package com.kolisnichenko2828.itemtracker.presentation.navigation

import android.content.Intent
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
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
    initialIntent: Intent? = null
) {
    val startId = initialIntent?.getIntExtra("last_viewed_id", -1) ?: -1
    val backStack = rememberSaveable {
        val stack = mutableStateListOf<Screen>(Screen.List)
        if (startId != -1) {
            stack.add(Screen.Item(startId))
        }
        stack
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