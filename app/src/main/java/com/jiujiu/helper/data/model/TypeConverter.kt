package com.jiujiu.helper.data.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromCalenderToLong(calendar: Calendar) = calendar.timeInMillis

    @TypeConverter
    fun fromLongToCalender(time: Long) : Calendar = Calendar.getInstance().apply { timeInMillis = time }

    @TypeConverter
    fun fromCurrencyToCode(currency: Currency): String = currency.currencyCode

    @TypeConverter
    fun fromCodeToCurrenty(code: String) : Currency = Currency.getInstance(code)
}