package com.example.talk_to_me

import android.content.Context
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Scroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.DetailScreen.route) {
            DetailScreen()
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Surface(color = Color.White) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val usernameState = remember { mutableStateOf("") }
            val passwordState = remember { mutableStateOf("") }
            val restIP = remember{ mutableStateOf("") }
            val restPort = remember{ mutableStateOf("") }
            val socketPort = remember{ mutableStateOf("") }
            val iceTimeout = remember{ mutableStateOf("") }
            val useTurn = remember{ mutableStateOf(false) }
            val enableTCPConnection = remember{ mutableStateOf(false) }
            val accountOptions = listOf("Account 1", "Account 2", "Account 3")
            val ringingFeedBackOptions = listOf("App","Server","Auto")
            var selectedRingingFeedback by remember { mutableStateOf(ringingFeedBackOptions[0]) }
            var selectedAccount by remember { mutableStateOf(accountOptions[0]) }
            var acExpanded by remember { mutableStateOf(false) }
            var apExpanded by remember { mutableStateOf(false) }



            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)){
                OutlinedTextField(
                    value = selectedAccount,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( 8.dp),
                    label = {  },
                    trailingIcon = {
                        Icon(
                            imageVector = if (acExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { acExpanded = !acExpanded }
                        )
                        DropdownMenu(
                            expanded = acExpanded,
                            onDismissRequest = { acExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            accountOptions.forEachIndexed { index,  account ->
                                DropdownMenuItem(
                                    onClick =   {
                                        selectedAccount = account
                                        acExpanded = false
                                        Toast.makeText(context, selectedAccount, Toast.LENGTH_LONG).show()
                                    },
                                    text = { Text(account)}
                                )
                            }
                        }
                    }
                )
            }

            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text(text = "Username") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { keyboardController?.showSoftwareKeyboard() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hideSoftwareKeyboard()
                        performLogin(usernameState.value, passwordState.value, context)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = restIP.value,
                onValueChange = { restIP.value = it },
                label = { Text(text = "Rest Ip") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { keyboardController?.showSoftwareKeyboard() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = restPort.value,
                onValueChange = { restPort.value = it },
                label = { Text(text = "Rest Port") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { keyboardController?.showSoftwareKeyboard() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = socketPort.value,
                onValueChange = { socketPort.value = it },
                label = { Text(text = "Socket Port") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { keyboardController?.showSoftwareKeyboard() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = iceTimeout.value,
                onValueChange = { iceTimeout.value = it },
                label = { Text(text = "ICE Timeout") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { keyboardController?.showSoftwareKeyboard() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)){
                OutlinedTextField(
                    value = selectedRingingFeedback,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {  },
                    trailingIcon = {
                        Icon(
                            imageVector = if (apExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { apExpanded = !apExpanded }
                        )
                    }
                )

                DropdownMenu(
                    expanded = apExpanded,
                    onDismissRequest = { apExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ringingFeedBackOptions.forEachIndexed { index,  ringing ->
                        DropdownMenuItem(
                            onClick =   {
                                selectedRingingFeedback = ringing
                                apExpanded = false
                                Toast.makeText(context, selectedRingingFeedback, Toast.LENGTH_LONG).show()
                            },
                            text = {
                                Text(ringing)
                            }
                        )
                    }
                }
            }
            SwitchRow(labelType = "Use Turn", checked = useTurn.value, onCheckedChange = {isChecked -> useTurn.value = isChecked} )
            SwitchRow(labelType = "TCP Connection Keep Alive", checked =enableTCPConnection.value , onCheckedChange ={isChecked -> enableTCPConnection.value = isChecked } )
            Button(
                onClick = {
                    performLogin(usernameState.value, passwordState.value, context)
                    navController.navigate(Screens.MainScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {Text(text = "Login")}
        }
        }
    }


@Composable
fun SwitchRow(labelType: String, checked:Boolean, onCheckedChange:(Boolean)->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = labelType,
            modifier = Modifier.weight(1f)
            )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

    }
}



fun performLogin(username: String, password: String, context: Context) {

    if (username.isNotEmpty() && password.isNotEmpty()) {
        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Please enter valid username and password.", Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Surface(color= Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // DetailScreen içeriği
        }
    }
}

@Composable
fun DetailScreen() {
    Surface(color= Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // DetailScreen içeriği
        }
    }
}
