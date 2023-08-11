package com.orion.talktome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.orion.talktome.composeui.Register.view.RegisterCompose
import com.orion.talktome.repository.AccountsRepository
import com.orion.talktome.theme.TalkToMeTheme

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TalkToMeTheme {
        Surface {
            RegisterCompose()
        }
    }
}


