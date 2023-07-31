package com.orioninc.TalkToMe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orioninc.TalkToMe.composeui.RegisterCompose
import com.orioninc.TalkToMe.theme.Talk_To_MeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Talk_To_MeTheme {
                RegisterCompose()

            }
        }
    }
}

