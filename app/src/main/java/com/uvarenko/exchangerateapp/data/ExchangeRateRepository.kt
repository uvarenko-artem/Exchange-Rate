package com.uvarenko.exchangerateapp.data

import com.uvarenko.exchangerateapp.db.CurrencyDao
import com.uvarenko.exchangerateapp.db.CurrencyEntity
import com.uvarenko.exchangerateapp.db.ExchangeRateEntity
import com.uvarenko.exchangerateapp.db.ExchangeRateDao
import com.uvarenko.exchangerateapp.domain.CurrencyModel
import com.uvarenko.exchangerateapp.error.ErrorReceivingExchangeRates
import com.uvarenko.exchangerateapp.error.NoCurrenciesSelectedError
import com.uvarenko.exchangerateapp.network.ExchangeRateApi
import kotlinx.coroutines.flow.Flow

class ExchangeRateRepository(
    private val api: ExchangeRateApi,
    private val exchangeRateDao: ExchangeRateDao,
    private val currencyDao: CurrencyDao
) {

    suspend fun getCurrencies(): List<CurrencyModel> {
        val result = api.getCurrencies()
        if (result.isFailure) return emptyList()
        val available = result.getOrNull() ?: return emptyList()
        val selected = currencyDao.getCurrencies()
        return available.map { (name, _) ->
            CurrencyModel(
                name = name,
                selectedInitial = selected.contains(name)
            )
        }
    }

    suspend fun requestExchangeRates(): Result<Unit> {
        val currencies = currencyDao.getCurrencies()
        if (currencies.isEmpty()) return Result.failure(NoCurrenciesSelectedError())
        val result = api.getExchangeRates(currencies)
        if (result.isFailure) return Result.failure(ErrorReceivingExchangeRates())
        val data = result.getOrNull() ?: return Result.failure(ErrorReceivingExchangeRates())
        val rates = data.rates.map { (currency, rate) ->
            ExchangeRateEntity(
                currency = currency,
                rate = rate
            )
        }
        exchangeRateDao.insert(rates)
        return Result.success(Unit)
    }

    suspend fun addCurrency(currency: String) {
        currencyDao.insert(CurrencyEntity(currency))
    }

    suspend fun removeCurrency(currency: String) {
        currencyDao.delete(currency)
        exchangeRateDao.delete(currency)
    }

    fun getExchangeRates(): Flow<List<ExchangeRateEntity>> =
        exchangeRateDao.getExchangeRates()

}