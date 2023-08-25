package com.orioninc.talktome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orioninc.talktome.models.AccountsData
import com.genband.mobile.api.utilities.Configuration
import com.genband.mobilesdkdemo.ui.factory.RegistrationViewModelFactory
import com.genband.mobilesdkdemo.ui.login.RegisterViewModel
import com.orioninc.talktome.app.App

@Composable
fun RegisterCompose() {
    val viewModel: RegisterViewModel = viewModel(
        factory = RegistrationViewModelFactory(
            (LocalContext.current.applicationContext as App).serviceProvider
        )
    )
    var userNameTextFieldValue by rememberSaveable { mutableStateOf("") }
    var passwordTextFieldValue by rememberSaveable { mutableStateOf("") }
    var restIPTextFieldValue by rememberSaveable { mutableStateOf("") }
    var restPortTextFieldValue by rememberSaveable { mutableStateOf("") }
    var iceTimeoutTextFieldValue by rememberSaveable { mutableStateOf("") }
    var socketPortTextFieldValue by rememberSaveable { mutableStateOf("") }
    var useTurnSwitchTextFieldValue by rememberSaveable { mutableStateOf("") }


    var progressBarVisibleState by remember { mutableStateOf(false) }
    var selectedUserDataState by rememberSaveable { mutableStateOf<AccountsData?>(null) }

    val accountsData by viewModel.accounts.collectAsState()
    val accountNames = accountsData?.accountNames ?: emptyList()
    val accountsDataList = accountsData?.userSetList ?: emptyList()

    val configuration by remember { mutableStateOf(Configuration()) }
    val ringingFeedbackOption by remember { mutableStateOf("APP") }
    val useTurnSwitchState by remember { mutableStateOf(false) }


    MaterialTheme(colorScheme = darkColorScheme()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AccountDropdownMenu(
                    accountNames = accountNames,
                    accountsDataList = accountsDataList,
                    selectedUser = selectedUserDataState,
                    onUserSelected = { user ->
                        selectedUserDataState = user
                    }
                )

                // Other UI elements
                Text("Username")
                OutlinedTextField(
                    value = userNameTextFieldValue,
                    onValueChange = { userNameTextFieldValue = it },
                    label = { Text(text = "Enter Username") }
                )
                Text("Password")
                OutlinedTextField(
                    value = passwordTextFieldValue,
                    onValueChange = { passwordTextFieldValue = it },
                    label = { Text(text = "Enter Password") }
                )
                // if working add more


                Spacer(modifier = Modifier.height(16.dp))
                selectedUserDataState?.let { user ->
                    Text(text = "Selected User: ${user.device_user}")
                    // Add more fields here based on your requirements
                }
                // Register button
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        progressBarVisibleState = true
                        selectedUserDataState?.let { selectedUser ->
                            viewModel.setConfiguration(selectedUser)
                            viewModel.register()
                            viewModel.setSharedPrefs(selectedUser, useTurnSwitchState)
                        }
                    },
                    enabled = selectedUserDataState != null
                ) {
                    Text(text = "Register")
                }
            }

            // Show a progress indicator when the registration process
            if (progressBarVisibleState) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red // Customize the color if needed
                )
            }
        }
    }
     fun setUIValues(selectedUser: AccountsData) {
        userNameTextFieldValue = selectedUser.device_user
        passwordTextFieldValue = selectedUser.device_pass
        restIPTextFieldValue = selectedUser.config.restServerIP
        restPortTextFieldValue=selectedUser.config.restServerPort
        iceTimeoutTextFieldValue=selectedUser.config.ICECollectionTimeout.toString()
        socketPortTextFieldValue=selectedUser.config.webSocketServerPort
        val useTurnSwitchTextFieldValue = selectedUser.useTurn


    }
}


@Composable
fun AccountDropdownMenu(
    accountNames: List<String>,
    accountsDataList: List<AccountsData>,
    selectedUser: AccountsData?,
    onUserSelected: (AccountsData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedUser?.device_user ?: "",
            onValueChange = {},
            enabled = false,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        )
        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                accountNames.forEach { acname ->
                    DropdownMenuItem(
                        accountName = acname,
                        accountsDataList = accountsDataList,
                        onUserSelected = onUserSelected,
                        onCloseDropdown = { expanded = false }
                    )
                }
            }
        }

   selectedUser?.let {user ->
       Column(
               modifier = Modifier.fillMaxWidth(),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               OutlinedTextField(
                   value = user.device_user,
                   onValueChange = {},
                   label = { Text(text = "Username") },
                   enabled = false
               )
               // Add more OutlinedTextField elements for other user data fields
           OutlinedTextField(
               value = user.device_pass,
               onValueChange = {},
               label = { Text(text = "Password") },
               enabled = false
           )
           OutlinedTextField(
               value = user.config.restServerIP,
               onValueChange = {},
               label = { Text(text = "RestIp") },
               enabled = false
           )
           OutlinedTextField(
               value = user.config.restServerPort,
               onValueChange = {},
               label = { Text(text = "RestPort") },
               enabled = false
           )

           OutlinedTextField(
               value = user.config.ICECollectionTimeout.toString(),
               onValueChange = {},
               label = { Text(text = "IceTimeOut") },
               enabled = false
           )
           OutlinedTextField(
               value = user.config.webSocketServerPort,
               onValueChange = {},
               label = { Text(text = "SocketPort") },
               enabled = false
           )
//
         }
       }
    }
}



@Composable
fun DropdownMenuItem(
    accountName: String,
    accountsDataList: List<AccountsData>,
    onUserSelected: (AccountsData) -> Unit,
    onCloseDropdown: () -> Unit
) {
    androidx.compose.material.DropdownMenuItem(onClick = {
        val selectedUserData = accountsDataList.find { it.device_user == accountName }
        selectedUserData?.let { user ->
            onUserSelected(user)
            onCloseDropdown()
        }
    }) {
        Text(text = accountName)
    }
}



