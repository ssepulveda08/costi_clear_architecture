package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillEntityDao {

    @Query("SELECT * FROM BillEntity WHERE month = :month")
    fun getAllByMonth(month: Int): Flow<List<BillEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: BillEntity): Long

    @Query("DELETE FROM BillEntity WHERE id = :billId")
    fun deleteById(billId: Int)
}