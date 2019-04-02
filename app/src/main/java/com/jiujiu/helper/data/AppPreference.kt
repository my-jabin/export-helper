package com.jiujiu.helper.data

import android.content.Context
import android.content.SharedPreferences

import com.jiujiu.helper.di.scope.PreferenceInfo
import com.jiujiu.helper.util.AppConstant.PREF_KEY_USER_NAME

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
        context: Context,
        @PreferenceInfo preInfo: String
) {

    private val mPreference: SharedPreferences = context.getSharedPreferences(preInfo, Context.MODE_PRIVATE)

    var userName: String?
        get() = mPreference.getString(PREF_KEY_USER_NAME, null)
        set(userName) = mPreference.edit().putString(PREF_KEY_USER_NAME, userName).apply()

}
