package com.kolisnichenko2828.itemtracker.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.kolisnichenko2828.itemtracker.domain.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemsRepository @Inject constructor (
    private val dataStore: DataStore<Preferences>
) {
    private val LAST_ID_KEY = intPreferencesKey("last_viewed_id")

    fun getItems(): List<Item> {
        val items =  (0..19).map { id ->
            Item(
                id = id,
                name = "Item #$id",
                description = "Description $id"
            )
        }
        return items
    }

    fun getItemById(id: Int): Item? {
        return this.getItems().getOrNull(id)
    }

    suspend fun saveLastViewedId(id: Int) {
        dataStore.edit { preferences ->
            preferences.set(
                key = LAST_ID_KEY,
                value = id
            )
        }
    }

    fun getLastViewedId(): Flow<Int?> {
        val lastViewedId = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences.get(LAST_ID_KEY)
            }
        return lastViewedId
    }
}