package com.example.talk_to_me.app

import android.app.Application
import com.genband.mobile.ServiceProvider

class App : Application(){


    val serviceProvider: ServiceProvider by lazy {
        ServiceProvider.getInstance(this)
    }
}