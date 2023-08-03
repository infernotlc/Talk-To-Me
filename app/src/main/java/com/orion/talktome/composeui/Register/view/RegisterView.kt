package com.orion.talktome.composeui.Register.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orion.talktome.R
import com.orion.talktome.composeui.Register.model.AccountDropDownMenu


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterCompose() {

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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        AccountDropDownMenu()
                        Spacer(modifier = Modifier.height(8.dp))
                    // Username field
                    TextField(
                        value = usernameState,
                        onValueChange = { usernameState },
                        label = { Text(text =  "Username") },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_account_box_24), // Replace with your icon resource
                                contentDescription = "Profile Icon"
                            )
                        }
                    )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Password field
                    TextField(
                        value = passwordState,
                        onValueChange = { passwordState },
                        label = { Text(text =  "Password")},
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_lock_24), // Replace with your icon resource
                                contentDescription = "Profile Icon"
                            )
                        }
                    )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Rest IP field
                    TextField(
                        value = restIpState,
                        onValueChange = { restIpState = it },
                        label = { Text(text =  "Rest IP") }
                    )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Rest Port field
                    TextField(
                        value = restPortState,
                        onValueChange = { restPortState },
                        label = { Text(text = "Rest Port")},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Socket Port field
                    TextField(
                        value = socketPortState,
                        onValueChange = { socketPortState },
                        label = { Text(text = "Socket Port")},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                        Spacer(modifier = Modifier.height(8.dp))
                    // ICE Timeout field
                    TextField(
                        value = iceTimeoutState,
                        onValueChange = { iceTimeoutState },
                        label = { Text(text = "ICE Timeout")},
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                        Spacer(modifier = Modifier.height(8.dp))
                    // Ringing Feedback Option dropdown
                    AccountDropDownMenu()
                        Spacer(modifier = Modifier.height(8.dp))
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
                            onCheckedChange = { useTurnState = it},
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = Color.Blue,
                                uncheckedThumbColor = Color.Gray
                            )
                        )
                    }
                        Spacer(modifier = Modifier.height(8.dp))
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
                            onCheckedChange = { tcpConnectionKeepAliveState = it },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = Color.Blue,
                                uncheckedThumbColor = Color.Gray
                            )
                        )
                    }
                        Spacer(modifier = Modifier.height(8.dp))
                    // Submit button
                        Button(
                            onClick = onLoginButtonClick,
                            enabled = !usernameState.isBlank() && !passwordState.isBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp) // Add vertical padding to create spacing
                                .background(Color.Gray),
                            shape = MaterialTheme.shapes.medium, // Apply a shape to the button (rounded corners)
                            contentPadding = PaddingValues(2.dp) // Add padding inside the button
                        ) {
                            Text(
                                text = "Submit",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        )
    }
}

