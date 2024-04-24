package com.ssepulveda.costi.data.repository.costType

import com.ssepulveda.costi.data.source.local.dao.TypeOfExpenseDao
import com.ssepulveda.costi.data.source.local.entities.TypeOfExpense
import com.ssepulveda.costi.domain.entity.CostType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class LocalCostTypeRepositoryImplTest {

    private val typeOfExpenseDao = mock<TypeOfExpenseDao>()
    private val costTypeRepository = LocalCostTypeRepositoryImpl(typeOfExpenseDao)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetAllCostType() = runTest {
        val localCostType = listOf(TypeOfExpense(1, "name"))
        val expectedCostType = listOf(CostType(1, "name"))

        Mockito.`when`(typeOfExpenseDao.getAll())
            .thenReturn(flowOf(localCostType))

        val result = costTypeRepository.getAllCostType().first()
        Assert.assertEquals(expectedCostType, result)
    }


}
