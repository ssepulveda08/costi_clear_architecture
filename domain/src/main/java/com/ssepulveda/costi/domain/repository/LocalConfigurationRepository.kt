package com.ssepulveda.costi.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalConfigurationRepository {

    fun hasInitialConfiguration(): Flow<Boolean>

    suspend fun savedInitialConfiguration()

    fun getMonthSet(): Flow<Int>

    suspend fun saveMonth(month: Int)
}