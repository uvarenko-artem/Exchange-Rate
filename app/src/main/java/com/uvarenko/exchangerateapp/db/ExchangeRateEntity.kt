package com.uvarenko.exchangerateapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_id")
    val currency: String,
    @ColumnInfo(name = "rate")
    val rate: Double
)