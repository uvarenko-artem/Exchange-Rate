package com.uvarenko.exchangerateapp.core

import com.uvarenko.exchangerateapp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun buildHttpClient() =
    HttpClient(Android) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "openexchangerates.org"
                path("api/")
                parameters.append("app_id", BuildConfig.APP_ID)
                parameters.append("prettyprint", "false")
                parameters.append("show_alternative", "false")
            }
        }
    }
