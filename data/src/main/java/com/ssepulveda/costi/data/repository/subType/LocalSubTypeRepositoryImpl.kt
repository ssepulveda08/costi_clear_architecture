package com.ssepulveda.costi.data.repository.subType

import android.util.Log
import com.ssepulveda.costi.data.source.local.dao.SubTypeDao
import com.ssepulveda.costi.data.source.local.entities.SubType
import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalSubTypeRepositoryImpl(
    private val subTypeDao: SubTypeDao
) : LocalSubTypeRepository {

    override fun getAllSubType(): Flow<List<SubTypeCost>> {
        Log.d("POTATO", "getAllSubType")
        return subTypeDao.loadAllB().map { subTypes ->
            subTypes.map { SubTypeCost(it.id, it.name, it.type) }
        }
    }

    override fun getAllSubTypeByType(costType: CostType): Flow<List<SubTypeCost>> {
        return subTypeDao.loadAllByIds(arrayOf(costType.id).toIntArray()).map { subTypes ->
            subTypes.map { SubTypeCost(it.id, it.name, it.type) }
        }
    }

    override fun deleteSubType(subType: SubTypeCost, type: Int) {
        subTypeDao.delete(SubType(subType.id, type, subType.name))
    }

    override suspend fun addSubTypes(subTypes: List<SubTypeCost>, type: Int) {
        val list: List<SubType> = subTypes.map { SubType(it.id, type,  it.name,) }
        subTypeDao.insertAll(list)
    }

}
