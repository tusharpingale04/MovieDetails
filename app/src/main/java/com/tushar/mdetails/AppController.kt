package com.tushar.mdetails

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}