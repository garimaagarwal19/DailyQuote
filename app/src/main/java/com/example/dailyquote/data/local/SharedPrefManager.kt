package com.example.dailyquote.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.dailyquote.MyApplication

/**
 * Created by Garima Chamaria on 05 October,2020
 */

object SharedPrefManager {
    private var prefManager: SharedPreferences? = null
    private val PREF_NAME = "app_preferences"

    private val IS_FIRST_LAUNCH = "is_first_launch"

    private fun getSharedPreferences() : SharedPreferences {
        if(prefManager == null) {
            prefManager = MyApplication.getAppContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
        return prefManager!!
    }

    var isFirstLaunch: Boolean
        get() = getSharedPreferences().getBoolean(IS_FIRST_LAUNCH, true)
        set(value) = getSharedPreferences().putBoolean(IS_FIRST_LAUNCH, value)
}

//Extension Function
fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}
