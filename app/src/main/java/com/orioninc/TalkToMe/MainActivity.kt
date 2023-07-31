package com.orioninc.TalkToMe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orioninc.TalkToMe.theme.TalkToMeTheme
import com.orioninc.TalkToMe.composeui.RegisterCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalkToMeTheme {
          RegisterCompose()

}
            }
        }
    }


