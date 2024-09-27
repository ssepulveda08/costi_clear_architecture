package com.ssepulveda.costi.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ssepulveda.costi.domain.entity.Month
import java.util.Date


@Entity(
    foreignKeys = [ForeignKey(
        entity = SubType::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subType"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val subType: Int,
    val description: String,
    val value: Double,
    val month: Int,
    @ColumnInfo(defaultValue = "7777") val accountId: Int,
    val recordDate: String,
    val updateDate: String,
)