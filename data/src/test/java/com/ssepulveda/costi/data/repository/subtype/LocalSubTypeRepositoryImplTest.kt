package com.ssepulveda.costi.data.repository.subtype


import com.ssepulveda.costi.data.repository.subType.LocalSubTypeRepositoryImpl
import com.ssepulveda.costi.data.source.local.dao.SubTypeDao
import com.ssepulveda.costi.data.source.local.entities.SubType
import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class LocalSubTypeRepositoryImplTest {

    private val subTypeDao = Mockito.mock<SubTypeDao>()
    private val subTypeRepository = LocalSubTypeRepositoryImpl(subTypeDao)

    @Test
    fun testGetAllCostType() = runTest {
        val localSubType = listOf(SubType(1,58, "type1"))
        val expectedSubType = listOf(SubTypeCost(1, "type1"))

        Mockito.`when`(subTypeDao.loadAllByIds(arrayOf(58).toIntArray()))
            .thenReturn(flowOf(localSubType))

        val result = subTypeRepository.getAllSubTypeByType(CostType(58, "name")).first()
        Assert.assertEquals(expectedSubType, result)
    }

}