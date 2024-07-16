package com.ssepulveda.costi.domain.useCase

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.types.GetCostTypeWithSubTypeUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetCostTypeWithSubTypeUseCaseTest {

    private val localCostTypeRepository = mock<LocalCostTypeRepository>()
    private val localSubTypeRepository = mock<LocalSubTypeRepository>()

    val useCase = GetCostTypeWithSubTypeUseCase(
        mock(),
        localCostTypeRepository,
        localSubTypeRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetAllCostType() = runTest {
        val type1 = CostType(1, "name1")
        val type2 = CostType(2, "name2")
        val subType1 = SubTypeCost(1, "name1")
        val subType2 = SubTypeCost(2, "name2")
        val subType3 = SubTypeCost(2, "name3")

        Mockito.`when`(localCostTypeRepository.getAllCostType())
            .thenReturn(flowOf(listOf(type1, type2)))

        Mockito.`when`(localSubTypeRepository.getAllSubTypeByType(type1))
            .thenReturn(flowOf(listOf(subType1, subType2)))

        Mockito.`when`(localSubTypeRepository.getAllSubTypeByType(type2))
            .thenReturn(flowOf(listOf(subType1, subType2, subType3)))

        val resultType1 = type1.copy(subTypes = listOf(subType1, subType2))
        val resultType2 = type2.copy(subTypes = listOf(subType1, subType2, subType3))



        //backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val response = useCase.process(GetCostTypeWithSubTypeUseCase.Request).first()
            assertEquals(
                GetCostTypeWithSubTypeUseCase.Response(
                    listOf(
                        resultType1,
                        resultType2
                    )
                ),
                response
            )
        //}
    }


}
