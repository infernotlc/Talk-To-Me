package com.orioninc.talktome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.genband.mobile.ServiceProvider
import com.genband.mobile.api.services.call.CallServiceInterface
import com.genband.mobile.api.services.im.ImServiceInterface
import com.genband.mobile.api.services.presence.PresenceServiceInterface
import com.genband.mobile.api.services.push.PushServiceInterface
import com.orioninc.talktome.app.App
import com.orioninc.talktome.theme.TalkToMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalkToMeTheme {
                val appInstance = LocalContext.current.applicationContext as App
                val serviceProvider = appInstance.serviceProvider

                // Remember the serviceProvider so it persists across recompositions
                val rememberedServiceProvider = remember { serviceProvider }

                // Call your Compose function with the serviceProvider
                RegisterCompose(serviceProvider = rememberedServiceProvider)
            }
        }
    }
}
@Composable
fun RegisterCompose(serviceProvider: ServiceProvider) {
    val callService: CallServiceInterface = serviceProvider.getCallService()
    val presenceService: PresenceServiceInterface = serviceProvider.getPresenceService()
    val imService: ImServiceInterface = serviceProvider.getIMService()
    val pushService: PushServiceInterface = serviceProvider.getPushService()
}





