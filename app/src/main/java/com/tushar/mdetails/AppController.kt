package com.tushar.mdetails

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base class which is called when app is launched
 */
@HiltAndroidApp
class AppController : Application()