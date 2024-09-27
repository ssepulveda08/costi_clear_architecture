package com.ssepulveda.costi.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssepulveda.costi.data.source.local.dao.AccountDao
import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.data.source.local.dao.SubTypeDao
import com.ssepulveda.costi.data.source.local.dao.TypeOfExpenseDao
import com.ssepulveda.costi.data.source.local.entities.Account
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import com.ssepulveda.costi.data.source.local.entities.SubType
import com.ssepulveda.costi.data.source.local.entities.TypeOfExpense

@Database(entities = [
    TypeOfExpense::class,
    SubType::class,
    BillEntity::class,
    Account::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subTypeDao(): SubTypeDao
    abstract fun typeOfExpenseDao(): TypeOfExpenseDao
    abstract fun billEntityDao(): BillEntityDao
    abstract fun reportForMonthDao(): ReportForMonthDao
    abstract fun accountDao(): AccountDao

}
