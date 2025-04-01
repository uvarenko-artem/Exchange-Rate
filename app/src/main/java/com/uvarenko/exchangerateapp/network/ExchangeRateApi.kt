package com.uvarenko.exchangerateapp.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ExchangeRateApi(private val httpClient: HttpClient) {

    suspend fun getCurrencies(): Result<Map<String, String>> =
        kotlin.runCatching {
            httpClient.get("currencies.json")
                .body<Map<String, String>>()
        }

    suspend fun getExchangeRates(currencies: List<String>): Result<ExchangeRateApiModel> =
        kotlin.runCatching {
            httpClient.get("latest.json") {
                parameter("symbols", currencies.joinToString(","))
            }
                .body<ExchangeRateApiModel>()
        }

}
