package com.uvarenko.exchangerateapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<ExchangeRateEntity>)

    @Query("SELECT * FROM exchange_rates")
    fun getExchangeRates(): Flow<List<ExchangeRateEntity>>

    @Query("DELETE FROM exchange_rates WHERE currency_id = :currency")
    suspend fun delete(currency: String)

}