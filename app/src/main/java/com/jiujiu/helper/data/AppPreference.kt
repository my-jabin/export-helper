package com.jiujiu.helper.data

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

import com.jiujiu.helper.di.scope.PreferenceInfo
import com.jiujiu.helper.util.AppConstant.PREF_KEY_DEFAULT_PURCHASE_CURRENCY_CODE
import com.jiujiu.helper.util.AppConstant.PREF_KEY_DEFAULT_SALE_CURRENCY_CODE
import com.jiujiu.helper.util.AppConstant.PREF_KEY_USER_NAME
import org.jetbrains.anko.configuration
import java.util.*

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Singleton
class AppPreference @Inject constructor(
        context: Context,
        @PreferenceInfo preInfo: String
) {

    private val mPreference: SharedPreferences = context.getSharedPreferences(preInfo, Context.MODE_PRIVATE)

    var userName: String? by StringPreference(mPreference, PREF_KEY_USER_NAME, FirebaseAuth.getInstance().currentUser?.displayName)

    var purchaseCurrencyCode: String? by StringPreference(mPreference, PREF_KEY_DEFAULT_PURCHASE_CURRENCY_CODE, Currency.getInstance(context.configuration.locales[0]).currencyCode)

    var saleCurrencyCode: String? by StringPreference(mPreference, PREF_KEY_DEFAULT_SALE_CURRENCY_CODE, "CNY")
}

class StringPreference(
        private val preference: SharedPreferences,
        private val name: String,
        private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preference.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preference.edit().putString(name, value).apply()
    }

}
