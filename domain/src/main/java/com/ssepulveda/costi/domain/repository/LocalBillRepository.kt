package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.Bill
import kotlinx.coroutines.flow.Flow

interface LocalBillRepository {

    fun getBillById(billId: Int): Flow<Bill?>

    fun getAllBillsByMonth(month: Int, accountId: Int): Flow<List<Bill>?>

    suspend fun addBill(bill: Bill): Flow<Long>

    suspend fun deleteBill(bill: Bill)

    suspend fun updateBill(bill: Bill)
}