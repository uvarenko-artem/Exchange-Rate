package com.uvarenko.exchangerateapp.domain

import com.uvarenko.exchangerateapp.data.ExchangeRateRepository

class RemoveCurrencyUseCase(private val repository: ExchangeRateRepository) {

    suspend operator fun invoke(currency: String) {
        repository.removeCurrency(currency)
    }

}