package com.ssepulveda.costi.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val month: Int,
    val name: String,
    val capped: Double,
)