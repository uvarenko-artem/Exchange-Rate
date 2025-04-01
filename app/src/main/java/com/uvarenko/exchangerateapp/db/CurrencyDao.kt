package com.uvarenko.exchangerateapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CurrencyEntity)

    @Query("SELECT currency_id FROM currency")
    suspend fun getCurrencies(): List<String>

    @Query("DELETE FROM currency WHERE currency_id = :currency")
    suspend fun delete(currency: String)

}