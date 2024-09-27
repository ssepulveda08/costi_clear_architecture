package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ssepulveda.costi.data.source.local.entities.BillAndInformation
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillEntityDao {

    @Query("Select bill.*, subType.id as subType_id, subType.name as subType_name, type.id as type_id, type.name as type_name \n" +
            "from BillEntity as bill \n" +
            "INNER JOIN SubType as subType\n" +
            "ON bill.subType = subType.id  \n" +
            "INNER JOIN TypeOfExpense as type\n" +
            "ON subType.type = type.id\n" +
            "WHERE month = :month AND accountId in(:accountId) " +
            "ORDER BY recordDate DESC" )
    fun getAllByMonth(month: Int, accountId: Int): Flow<List<BillAndInformation>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: BillEntity): Long

    @Query("DELETE FROM BillEntity WHERE id = :billId")
    fun deleteById(billId: Int)

    @Query("SELECT * FROM BillEntity WHERE ID = :billId")
    fun getBillByID(billId: Int) : Flow<BillEntity>

    @Update
    suspend fun update(bill: BillEntity)
}