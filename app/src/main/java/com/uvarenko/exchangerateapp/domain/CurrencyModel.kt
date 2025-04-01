package com.uvarenko.exchangerateapp.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class CurrencyModel(
    val name: String,
    val selectedInitial: Boolean
) {

    var selected by mutableStateOf(selectedInitial)

}