package com.ssepulveda.costi.data.source.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [ForeignKey(
        entity = TypeOfExpense::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("type"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubType(
    @PrimaryKey(autoGenerate = true) val id: Int? =null,
    val type : Int,
    val name: String,
)
