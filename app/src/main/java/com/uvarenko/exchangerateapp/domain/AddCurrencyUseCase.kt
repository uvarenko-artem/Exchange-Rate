package com.uvarenko.exchangerateapp.domain

import com.uvarenko.exchangerateapp.data.ExchangeRateRepository

class AddCurrencyUseCase(private val repository: ExchangeRateRepository) {

    suspend operator fun invoke(currencyModel: CurrencyModel) {
        repository.addCurrency(currencyModel.name)
    }

}