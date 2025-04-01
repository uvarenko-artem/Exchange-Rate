package com.uvarenko.exchangerateapp.error

class NoCurrenciesSelectedError: Throwable("You should select currency")

class ErrorReceivingExchangeRates: Throwable("Something went wrong =(")