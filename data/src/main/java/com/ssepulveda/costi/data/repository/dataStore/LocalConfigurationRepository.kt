package com.ssepulveda.costi.data.repository.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val KEY_INITIAL_CONFIGURATION = booleanPreferencesKey("key_initial_configuration")
internal val KEY_MONTH_SET = intPreferencesKey("key_month_set")

class LocalConfigurationRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    LocalConfigurationRepository {

    override fun hasInitialConfiguration(): Flow<Boolean> {
        return dataStore.data.map {
            it[KEY_INITIAL_CONFIGURATION] ?: false
        }
    }


    override suspend fun savedInitialConfiguration() {
        dataStore.edit {
            it[KEY_INITIAL_CONFIGURATION] = true
        }
    }

    override fun getMonthSet(): Flow<Int> {
        return dataStore.data.map { it[KEY_MONTH_SET] ?: 0 }
    }

    override suspend fun saveMonth(month: Int) {
        dataStore.edit {
            it[KEY_MONTH_SET] = month
        }
    }
}