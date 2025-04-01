package com.uvarenko.exchangerateapp.di

import androidx.room.Room
import com.uvarenko.exchangerateapp.db.ExchangeRateDatabase
import com.uvarenko.exchangerateapp.domain.AddCurrencyUseCase
import com.uvarenko.exchangerateapp.domain.RemoveCurrencyUseCase
import com.uvarenko.exchangerateapp.domain.GetExchangeRatesUseCase
import com.uvarenko.exchangerateapp.domain.GetCurrenciesUseCase
import com.uvarenko.exchangerateapp.data.ExchangeRateRepository
import com.uvarenko.exchangerateapp.core.buildHttpClient
import com.uvarenko.exchangerateapp.network.ExchangeRateApi
import com.uvarenko.exchangerateapp.ui.home.HomeViewModel
import com.uvarenko.exchangerateapp.ui.currency.CurrencyViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ExchangeRateDatabase::class.java,
            "exchange-rates-database"
        ).build()
    }
    single { get<ExchangeRateDatabase>().exchangeRateDao() }
    single { get<ExchangeRateDatabase>().currencyDao() }
}

val coreModule = module { single { buildHttpClient() } }

val networkModule = module { singleOf(::ExchangeRateApi) }

val dataModule = module { singleOf(::ExchangeRateRepository) }

val domainModule = module {
    factoryOf(::AddCurrencyUseCase)
    factoryOf(::RemoveCurrencyUseCase)
    factoryOf(::GetExchangeRatesUseCase)
    factoryOf(::GetCurrenciesUseCase)
}

val vmModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CurrencyViewModel)
}

val appModules = listOf(
    dbModule,
    coreModule,
    networkModule,
    dataModule,
    domainModule,
    vmModule
)
