package com.ssepulveda.costi.data.repository.bills

import android.os.Build
import android.util.Log
import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.Date

class LocalBillRepositoryImpl(
    private val billEntityDao: BillEntityDao
) : LocalBillRepository {

    override fun getBillById(billId: Int): Flow<Bill?> =
        billEntityDao.getBillByID(billId).map {
            Bill(
                id = it.id ?:0,
                subType = it.subType,
                description = it.description,
                value = it.value,
                month = it.month,
                recordDate = it.recordDate,
                updateDate = it.recordDate,
            )
        }

    override fun getAllBillsByMonth(month: Int): Flow<List<Bill>> =
        billEntityDao.getAllByMonth(month).map { list ->
            list?.map {
                Bill(
                    id = it.id ?: 0,
                    subType = it.subType,
                    description = it.description,
                    value = it.value.toDouble(),
                    month = it.month,
                    recordDate = it.recordDate,
                    updateDate = it.recordDate,
                )
            } ?: listOf()
        }

    override suspend fun addBill(bill: Bill): Flow<Long> {

        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().toString()
        } else {
            Date()
        }
        return flow<Long> {
            val id = billEntityDao.insert(
                BillEntity(
                    description = bill.description,
                    subType = bill.subType,
                    value = bill.value,
                    month = bill.month,
                    recordDate = date.toString(),
                    updateDate = date.toString(),
                )
            )
            emit(id)
        }
    }

    override suspend fun deleteBill(bill: Bill) {
        bill.id?.let { id ->
            billEntityDao.deleteById(id)
        }
    }

    override suspend fun updateBill(bill: Bill) {
        val billUpdate = BillEntity(
            id = bill.id,
            description = bill.description,
            subType = bill.subType,
            value = bill.value,
            month = bill.month,
            recordDate = bill.recordDate,
            updateDate = bill.updateDate
        )
        billEntityDao.update(billUpdate)
    }

}