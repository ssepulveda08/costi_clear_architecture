package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost
import kotlinx.coroutines.flow.Flow


interface LocalSubTypeRepository {

     fun getAllSubType() : Flow<List<SubTypeCost>>

     fun getAllSubTypeByType(costType: CostType) : Flow<List<SubTypeCost>>

     fun deleteSubType(subType: SubTypeCost, type: Int)

     suspend fun addSubTypes(subTypes: List<SubTypeCost>, type: Int)
}