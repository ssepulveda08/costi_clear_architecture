package com.ssepulveda.costi.data.source.local.entities

import androidx.room.ColumnInfo

data class BillAndInformation(
    val id: Int,
    val subType: Int,
    val description: String,
    val value: Double,
    val month: Int,
    val recordDate: String,
    val updateDate: String,
    @ColumnInfo(name = "subType_id")
    val subTypeId: Int,
    @ColumnInfo(name = "subType_name")
    val subTypeName: String,
    @ColumnInfo(name = "type_id")
    val typeId: Int,
    @ColumnInfo(name = "type_name")
    val typeName: String
)
