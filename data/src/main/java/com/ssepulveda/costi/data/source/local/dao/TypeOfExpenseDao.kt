package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.TypeOfExpense
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeOfExpenseDao {

    @Query("SELECT * FROM typeOfExpense")
    fun getAll(): Flow<List<TypeOfExpense>>

    @Query("SELECT * FROM typeOfExpense WHERE id IN (:typeIds)")
    fun loadAllByIds(typeIds: IntArray): Flow<List<TypeOfExpense>>

    @Query("SELECT * FROM typeOfExpense WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Flow<TypeOfExpense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubType(type: TypeOfExpense): Long

    @Delete
    fun delete(user: TypeOfExpense)
}
