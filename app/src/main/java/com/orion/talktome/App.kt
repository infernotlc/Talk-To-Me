package com.orion.talktome

import android.app.Application
import com.genband.mobile.ServiceProvider

class TalkToMe : Application(){


    val serviceProvider: ServiceProvider by lazy {
        ServiceProvider.getInstance(this)
    }
}