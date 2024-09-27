package com.ssepulveda.costi.data.source.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        val newTable = """
            CREATE TABLE IF NOT EXISTS `Account` (
                `month` INTEGER NOT NULL,
                `capped` DOUBLE NOT NULL,
                `name` TEXT NOT NULL,
                `id` INTEGER PRIMARY KEY AUTOINCREMENT
            )
        """

        // created new table accounten
        db.execSQL(newTable)
        // add new column in bills
        db.execSQL("ALTER TABLE BillEntity ADD COLUMN accountId INTEGER NOT NULL DEFAULT 7777")
        for (i in 1..12) {
            val values = ContentValues().apply {
                put("id", i)
                put("month", i)
                put("name", "Principal")
                put("capped", 0.0)
            }
            // insert roes account for month
            db.insert("Account", CONFLICT_IGNORE, values)

            // update bill by month
            db.execSQL("UPDATE BillEntity\n" +
                    "SET accountId = $i\n" +
                    "WHERE month = $i;")
        }

    }
}
