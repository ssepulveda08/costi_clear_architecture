package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.Bill
import kotlinx.coroutines.flow.Flow

interface LocalBillRepository {

    fun getAllBillsByMonth(month: Int): Flow<List<Bill>?>

    suspend fun addBill(bill: Bill): Flow<Long>

    suspend fun deleteBill(bill: Bill)
}