package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.CostType
import kotlinx.coroutines.flow.Flow

interface LocalCostTypeRepository{

    fun getAllCostType(): Flow<List<CostType>>

    suspend fun addCostType(costType: CostType): CostType
}
