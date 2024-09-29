package com.balsha.forecasttask.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application() {
    init {
        application = this
    }

    companion object {
        private lateinit var application: Application
        fun getApplicationContext(): Context = application.applicationContext
    }
}