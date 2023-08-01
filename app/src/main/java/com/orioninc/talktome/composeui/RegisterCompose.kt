package com.orioninc.talktome.composeui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orioninc.talktome.models.AccountsData
import com.orioninc.talktome.models.Configurations
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
    val progressBarVisibleState = remember { mutableStateOf(false) }
    val selectedUserDataState = rememberSaveable {
        mutableStateOf<AccountsData?>(null)
    }
  val selectedConfigState= rememberSaveable {
      mutableStateOf<Configurations?>(null)
  }
    val selectedUser: AccountsData? by remember { selectedUserDataState }
    val selectedConfig:Configurations? by remember {selectedConfigState}

    var userName by remember { mutableStateOf(selectedUser?.device_user ?: "") }
    var password by remember { mutableStateOf(selectedUser?.device_pass ?: "") }
    var restIP by remember { mutableStateOf(selectedConfig?.restServerIP ?: "") }
    var restPort by remember { mutableStateOf(selectedConfig?.restServerPort ?: "") }
    var socketPort by remember { mutableStateOf(selectedConfig?.webSocketServerPort ?: "") }
    var iceTimeout by remember { mutableStateOf(selectedConfig?.ICECollectionTimeout ?: "") }
    var useTurnSwitchState by remember { mutableStateOf(selectedUser?.useTurn ?: false) }
    var socketServerId by remember { mutableStateOf(selectedConfig?.webSocketServerIP ?: false) }
    var accountsData by viewModel.accountsData.collectAsState()
    var accountNames = accountsData?.accountNames ?: emptyList()
    var accountsDataList = accountsData?.userSetList ?: emptyList()

    val configuration by remember { mutableStateOf(Configuration()) }
    var ringingFeedbackOption by remember { mutableStateOf("APP") }

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
                // Username
                InputField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = "Username",
                    leadingIcon = Icons.Default.Person
                )

                // Password
                InputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password
                )
                // Rest IP
                InputField(
                    value = restIP,
                    onValueChange = { restIP = it },
                    label = "Rest IP"
                )
                // Rest Port
                InputField(
                    value = restPort,
                    onValueChange = { restPort = it },
                    label = "Rest Port",
                    keyboardType = KeyboardType.Number
                )
                // Socket Port
                InputField(
                    value = socketPort,
                    onValueChange = { socketPort = it },
                    label = "Socket Port",
                    keyboardType = KeyboardType.Number
                )
                // ICE Timeout
                InputField(
                    value = iceTimeout,
                    onValueChange = { iceTimeout = it },
                    label = "ICE Timeout",
                    keyboardType = KeyboardType.Number
                )
                // Ringing Feedback Option
                OutlinedTextField(
                    value = ringingFeedbackOption,
                    onValueChange = { ringingFeedbackOption = it },
                    label = { Text("Ringing Feedback Option") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            focusManager.clearFocus() // Dismiss keyboard when opening the dropdown
                        },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            tint = Color.Black
                        )
                    }
                )
                // Use Turn Switch
                SwitchRow(
                    label = "Use Turn",
                    checked = useTurn,
                    onCheckedChange = { useTurn = it }
                )

                // TCP Connection Keep Alive Switch
                SwitchRow(
                    label = "TCP Connection Keep Alive",
                    checked = enableTCPConnection,
                    onCheckedChange = { enableTCPConnection = it }
                )





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
    configurations: Configurations?,
    onUserSelected: (AccountsData, Configurations) -> Unit
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
    }
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accountNames.forEach { name ->
                DropdownMenuItem(
                    onClick = {
                        val selectedUserData = accountsDataList.find { it.device_user == name }
                        if (selectedUserData != null) {
                            val selectedConfig = /* logic to find Configurations object for selectedUserData */
                                if (selectedConfig != null) {
                                    onUserSelected(selectedUserData, selectedConfig) // Pass the selected user data
                                }
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
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onImeActionPerformed: () -> Unit = {}
){

}


    @Composable
fun SwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }

