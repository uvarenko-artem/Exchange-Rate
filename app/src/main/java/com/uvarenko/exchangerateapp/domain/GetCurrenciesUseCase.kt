package com.uvarenko.exchangerateapp.domain

import com.uvarenko.exchangerateapp.data.ExchangeRateRepository

class GetCurrenciesUseCase(private val repository: ExchangeRateRepository) {

    suspend operator fun invoke(): List<CurrencyModel> = repository.getCurrencies()

}