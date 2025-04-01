package com.uvarenko.exchangerateapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExchangeRateEntity::class,
        CurrencyEntity::class
    ],
    version = 1
)
abstract class ExchangeRateDatabase : RoomDatabase() {

    abstract fun exchangeRateDao(): ExchangeRateDao

    abstract fun currencyDao(): CurrencyDao

}
