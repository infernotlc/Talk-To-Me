package com.orioninc.talktome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orioninc.talktome.theme.TalkToMeTheme
import com.orioninc.talktome.composeui.RegisterCompose

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


