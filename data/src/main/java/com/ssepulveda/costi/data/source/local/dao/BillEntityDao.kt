package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillEntityDao {

    @Query("SELECT * FROM BillEntity WHERE month = :month ORDER BY id DESC" )
    fun getAllByMonth(month: Int): Flow<List<BillEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: BillEntity): Long

    @Query("DELETE FROM BillEntity WHERE id = :billId")
    fun deleteById(billId: Int)

    @Query("SELECT * FROM BillEntity WHERE ID = :billId")
    fun getBillByID(billId: Int) : Flow<BillEntity>

    @Update
    suspend fun update(bill: BillEntity)
}