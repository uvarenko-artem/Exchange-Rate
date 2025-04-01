package com.uvarenko.exchangerateapp.domain

import com.uvarenko.exchangerateapp.data.ExchangeRateRepository
import com.uvarenko.exchangerateapp.db.ExchangeRateEntity
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive

class GetExchangeRatesUseCase(private val repository: ExchangeRateRepository) {

    operator fun invoke(): Flow<Pair<Result<Unit>, List<ExchangeRateEntity>>> =
        flow {
            emit(Unit)
            while (currentCoroutineContext().isActive) {
                delay(3_000)
                emit(Unit)
            }
        }
            .map { repository.requestExchangeRates() }
            .combine(repository.getExchangeRates()) { requestResult, data ->
                requestResult to data
            }

}
