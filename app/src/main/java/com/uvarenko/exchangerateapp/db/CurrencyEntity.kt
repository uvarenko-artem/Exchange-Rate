package com.uvarenko.exchangerateapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_id")
    val currency: String
)