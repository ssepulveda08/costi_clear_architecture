package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun loadDefaultData(): Flow<Boolean>

    fun getAccountsByMonth(month: Int) : Flow<List<Account>>

    suspend fun registerAccount(account: Account): Flow<Long>
}
