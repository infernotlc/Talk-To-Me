package com.example.talk_to_me.composeui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.talk_to_me.R
import com.example.talk_to_me.app.App
import com.example.talk_to_me.models.AccountsData
import com.genband.mobile.api.utilities.Configuration
import com.genband.mobilesdkdemo.ui.factory.RegistrationViewModelFactory
import com.genband.mobilesdkdemo.ui.login.RegisterViewModel

@Composable
fun RegisterCompose() {
    val viewModel: RegisterViewModel = viewModel(
        factory = RegistrationViewModelFactory(
            (LocalContext.current.applicationContext as App).serviceProvider
        )
    )
    val progressBarVisibleState = remember { mutableStateOf(false) }
    val selectedUserDataState = rememberSaveable {
        mutableStateOf<AccountsData?>(null)
    }

    val accountsData by viewModel.accounts.collectAsState()
    val accountNames = accountsData?.accountNames ?: emptyList()
    val accountsDataList = accountsData?.userSetList ?: emptyList()

    val selectedUser: AccountsData? by remember { selectedUserDataState }
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
                    selectedUser = selectedUser,
                    onUserSelected = { user ->
                        selectedUserDataState.value = user
                    }
                )

                // Other UI elements


                Spacer(modifier = Modifier.height(16.dp))

                // Register button
                Button(
                    onClick = {
                        progressBarVisibleState.value = true
                        selectedUser?.let { selectedUser ->
                            viewModel.setConfiguration(selectedUser)
                            viewModel.register()
                            viewModel.setSharedPrefs(selectedUser, useTurnSwitchState)
                        }
                    },
                    enabled = selectedUser != null
                ) {
                    Text(text = stringResource(R.string.register))
                }
            }

            // Show a progress indicator when the registration process
            if (progressBarVisibleState.value) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red // Customize the color if needed
                )
            }
        }
    }
}

@Composable
fun AccountDropdownMenu(
    accountNames: List<String>,
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
            // Fetch accounts from the database
            fetchAccountsFromDatabase(onUserSelected)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accountNames.forEach { name ->
                DropdownMenuItem(
                    onClick = {
                        val selectedUserData = accountsDataList.find { it.device_user == name }
                        if (selectedUserData != null) {
                            onUserSelected(selectedUserData)
                        }
                        expanded = false
                    }
                ) {
                    Text(text = name)
                }
            }
        }
    }
}

@Composable
private fun fetchAccountsFromDatabase(onUserSelected: (AccountsData) -> Unit) {
    val viewModel: RegisterViewModel = viewModel()
    val accountsDataList by viewModel.accounts.collectAsState()


}
