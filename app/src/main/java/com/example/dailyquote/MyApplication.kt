package com.example.dailyquote

import android.app.Application

/**
 * Created by Garima Chamaria on 14 October,2020
 */
class MyApplication : Application() {

    companion object {
        private lateinit var appContext: MyApplication

        fun getAppContext() = appContext
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}