package com.ssepulveda.costi.data.source.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*@Entity(
    foreignKeys = [ForeignKey(
        entity = BillEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idBill"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubBillEntity(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    val description: String,
    val value: Double,
    val idBill: Int,
)*/
