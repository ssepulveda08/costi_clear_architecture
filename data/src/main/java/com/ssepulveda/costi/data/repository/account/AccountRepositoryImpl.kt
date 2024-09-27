package com.ssepulveda.costi.data.repository.account

import android.util.Log
import com.ssepulveda.costi.data.source.local.dao.AccountDao
import com.ssepulveda.costi.domain.entity.Account
import com.ssepulveda.costi.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {

    override suspend fun loadDefaultData(): Flow<Boolean> = flow {
        Log.d("POTATO", "loadDefaultData")
        val ids = mutableListOf<Long>()
        for (i in 1..12) {
            val account = com.ssepulveda.costi.data.source.local.entities.Account(
                month = i,
                name = "Cuenta principal",
                capped = 0.0
            )
            ids.add(accountDao.insert(account))
        }
        emit(ids.size > 0)
    }

    override fun getAccountsByMonth(month: Int): Flow<List<Account>> =
        accountDao.getAccountsByMonth(month).map { list ->
            list?.map {
                Account(it.id, it.month, it.name, it.capped)
            } ?: listOf()
        }

    override suspend fun registerAccount(account: Account): Flow<Long> = flow {
        val id = accountDao.insert(
            com.ssepulveda.costi.data.source.local.entities.Account(
                month = account.month,
                name = account.name,
                capped = account.capped
            )
        )
        emit(id)
    }
}
