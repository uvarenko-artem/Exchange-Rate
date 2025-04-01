package com.uvarenko.exchangerateapp.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarenko.exchangerateapp.domain.AddCurrencyUseCase
import com.uvarenko.exchangerateapp.domain.CurrencyModel
import com.uvarenko.exchangerateapp.domain.GetCurrenciesUseCase
import com.uvarenko.exchangerateapp.domain.RemoveCurrencyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val removeCurrencyUseCase: RemoveCurrencyUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private var initialState = listOf<CurrencyModel>()
    private val _currencyState = MutableStateFlow(listOf<CurrencyModel>())
    val currencyState = _currencyState.asStateFlow()

    init {
        viewModelScope.launch {
            initialState = getCurrenciesUseCase()
            _currencyState.emit(initialState)
        }
    }

    fun filter(query: String) {
        viewModelScope.launch {
            _currencyState.emit(initialState.filter { it.name.contains(query, ignoreCase = true) })
        }
    }

    fun select(index: Int) {
        viewModelScope.launch {
            val currencyModel = _currencyState.value[index]
            if (currencyModel.selected) {
                removeCurrencyUseCase(currencyModel.name)
            } else {
                addCurrencyUseCase(currencyModel)
            }
            val currentState = _currencyState.value
            currentState[index].selected = !currentState[index].selected
        }

    }

}