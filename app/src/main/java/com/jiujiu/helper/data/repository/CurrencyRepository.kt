package com.jiujiu.helper.data.repository

import android.content.Context
import android.icu.util.Currency
import org.jetbrains.anko.configuration
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(context: Context) {

    private val currencySet: HashSet<Currency> = hashSetOf(
            Currency.getInstance(Locale.GERMANY),
            Currency.getInstance(context.configuration.locales[0]),
            Currency.getInstance(Locale.CHINA),
            Currency.getInstance(Locale.US),
            Currency.getInstance(Locale.UK)
    )

    fun getCurrency(locale: Locale): Currency {
        this.addCurrency(Currency.getInstance(locale))
        return Currency.getInstance(locale)
    }

    fun addCurrency(currency: Currency) {
        this.currencySet.add(currency)
    }

    fun getCurrencies() = this.currencySet;
}