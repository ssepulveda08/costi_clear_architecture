package com.ssepulveda.costi.data.repository.bills

import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.SubTypeCost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class LocalBillRepositoryImplTest {

    private val billEntityDao = Mockito.mock<BillEntityDao>()
    private val localBillRepositoryImpl = LocalBillRepositoryImpl(billEntityDao)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetAllCostType() = runTest {
        val localBillEmptyType = listOf(
            BillEntity(
                id = 1,
                subType = 1,
                description = "name",
                value = 2000.0,
                1,
                "",
                ""
            )
        )
        val expectedSubType = listOf(SubTypeCost(1, "type1"))
        val localBillType = listOf(
            Bill(
                id = 1,
                subType = 1,
                description = "name",
                value = 2000.0,
                1,
                "",
                ""
            )
        )

        Mockito.`when`(billEntityDao.getAllByMonth(1))
            .thenReturn(flowOf(localBillEmptyType))

        val result = localBillRepositoryImpl.getAllBillsByMonth(1).first()
        Assert.assertEquals(localBillType, result)
    }


}