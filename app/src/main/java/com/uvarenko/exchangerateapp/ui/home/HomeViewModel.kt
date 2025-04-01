package com.uvarenko.exchangerateapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarenko.exchangerateapp.db.ExchangeRateEntity
import com.uvarenko.exchangerateapp.domain.GetExchangeRatesUseCase
import com.uvarenko.exchangerateapp.domain.RemoveCurrencyUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val removeCurrencyUseCase: RemoveCurrencyUseCase
): ViewModel() {

    private var exchangeRateJob: Job? = null
    private val _exchangeRateSate = MutableStateFlow(emptyList<ExchangeRateEntity>())
    val exchangeRateSate = _exchangeRateSate.asStateFlow()

    private val _requestState = MutableStateFlow<Throwable?>(null)
     val requestState = _requestState.asStateFlow()

    init {
        requestExchangeRates()
    }

    fun remove(exchangeRate: ExchangeRateEntity) {
        viewModelScope.launch {
            removeCurrencyUseCase(exchangeRate.currency)
        }
    }

    private fun requestExchangeRates() {
        exchangeRateJob?.cancel()
        exchangeRateJob = viewModelScope.launch {
            getExchangeRatesUseCase()
                .collectLatest { (requestStatus, data) ->
                    _requestState.emit(requestStatus.exceptionOrNull())
                    _exchangeRateSate.emit(data)
                }
        }
    }

}
