package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account): Long

    @Query("SELECT * FROM Account WHERE month = :month ORDER BY id ASC")
    fun getAccountsByMonth(month: Int): Flow<List<Account>?>

}
