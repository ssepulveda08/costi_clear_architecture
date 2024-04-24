package com.ssepulveda.costi.data.repository.costType

import com.ssepulveda.costi.data.source.local.dao.TypeOfExpenseDao
import com.ssepulveda.costi.data.source.local.entities.TypeOfExpense
import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalCostTypeRepositoryImpl(
    private val typeOfExpenseDao: TypeOfExpenseDao
) : LocalCostTypeRepository {

    override fun getAllCostType(): Flow<List<CostType>> = typeOfExpenseDao.getAll().map { types ->
        types.map { CostType(it.id ?: 0, it.name) }
    }

    override suspend fun addCostType(costType: CostType): CostType {
       val newCostType = typeOfExpenseDao.insertSubType(TypeOfExpense(costType.id, costType.name))
       return CostType(newCostType.toInt(), costType.name)
    }

}
