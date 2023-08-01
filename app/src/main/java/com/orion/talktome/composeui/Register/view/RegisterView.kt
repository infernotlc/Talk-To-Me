package com.orion.talktome.composeui.Register.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.trimmedLength
import com.orion.talktome.composeui.Register.model.AccountDropDownMenu



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterCompose() {
    // Sample account names and ringing feedback options
    val accountNames = listOf("Account 1", "Account 2", "Account 3")
    val ringingFeedbackOptions = listOf("Option 1", "Option 2", "Option 3")

    // Remembering the state of input fields
    val usernameState by rememberSaveable { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var restIpState by remember { mutableStateOf("") }
    var restPortState by remember { mutableStateOf("") }
    var socketPortState by remember { mutableStateOf("") }
    var iceTimeoutState by remember { mutableStateOf("") }
    var selectedAccountState by remember { mutableStateOf<CharSequence?>(null) }
    var selectedRingingFeedbackState by remember { mutableStateOf<CharSequence?>(null) }
    var useTurnState by remember { mutableStateOf(false) }
    var tcpConnectionKeepAliveState by remember { mutableStateOf(false) }

    // Assuming you have a function to handle login button click
    val onLoginButtonClick: () -> Unit = {
        // Handle login button click here
    }

    MaterialTheme {
        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    AccountDropDownMenu()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Username field
                    OutlinedTextField(
                        value = usernameState,
                        onValueChange = { usernameState },
                        label = { "Username" }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = passwordState,
                        onValueChange = { passwordState },
                        label = { "Password"},
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Rest IP field
                    OutlinedTextField(
                        value = restIpState,
                        onValueChange = { restIpState = it },
                        label = { "Rest IP" }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Rest Port field
                    OutlinedTextField(
                        value = restPortState,
                        onValueChange = { restPortState },
                        label = {"Rest Port"},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Socket Port field
                    OutlinedTextField(
                        value = socketPortState,
                        onValueChange = { socketPortState },
                        label = {"Socket Port"},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ICE Timeout field
                    OutlinedTextField(
                        value = iceTimeoutState,
                        onValueChange = { iceTimeoutState },
                        label = {"ICE Timeout"},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Ringing Feedback Option dropdown
                    AccountDropDownMenu()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Use Turn switch
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Use Turn",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = useTurnState,
                            onCheckedChange = { useTurnState}
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // TCP Connection Keep Alive switch
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "TCP Connection Keep Alive",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = tcpConnectionKeepAliveState,
                            onCheckedChange = { tcpConnectionKeepAliveState }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit button
                    Button(
                        onClick = onLoginButtonClick,
                        enabled = !usernameState.isBlank() && !passwordState.isBlank()
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        )
    }
}

