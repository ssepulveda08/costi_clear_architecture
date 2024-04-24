package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.SubType
import kotlinx.coroutines.flow.Flow

@Dao
interface SubTypeDao {

    @Query("SELECT * FROM subType")
    fun loadAllB(): Flow<List<SubType>>

    @Query("SELECT * FROM subType WHERE type IN (:types)")
    fun loadAllByIds(types: IntArray): Flow<List<SubType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(types:  List<SubType>)

    @Delete
    fun delete(user: SubType)
}
