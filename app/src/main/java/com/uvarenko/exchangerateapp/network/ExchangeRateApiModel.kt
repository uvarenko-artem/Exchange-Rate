package com.uvarenko.exchangerateapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateApiModel(
    @SerialName("rates")
    val rates: Map<String, Double>
)