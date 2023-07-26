@file:OptIn(ExperimentalMaterial3Api::class)

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.compose.navArgument
import androidx.navigation.fragment.findNavController
import com.example.talk_to_me.R
import com.example.talk_to_me.composeui.RegisterViewModel
import com.example.talk_to_me.models.AccountsData
import com.genband.mobilesdkdemo.ui.factory.RegistrationViewModelFactory
import com.google.android.material3.button.MaterialButton
import com.google.android.material3.progressindicator.LinearProgressIndicator
import com.google.android.material3.switchmaterial.Switch
import java.time.format.TextStyle

@Composable
fun RegisterFragment(navController: NavHostController) {
    val viewModel: RegisterViewModel by viewModels {
        RegistrationViewModelFactory((LocalContext.current.applicationContext as App).serviceProvider)
    }
    val accountsState by viewModel.accounts.observeAsState()
    var selectedUser by rememberSaveable { mutableStateOf<AccountsData?>(null) }
    var accountNames by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    var useTurn by rememberSaveable { mutableStateOf(false) }
    var progressBarVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        BackHandler {
            navController.popBackStack()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                BasicTextField(
                    value = selectedUser?.device_user?.let { TextFieldValue(it) } ?: TextFieldValue(""),
                    onValueChange = { selectedUser?.device_user = it.text },
                    singleLine = true,
                    label = { Text("User Name") }
                )

                BasicTextField(
                    value = TextFieldValue(selectedUser?.device_pass.orEmpty()),
                    onValueChange = { selectedUser?.device_pass = it.text },
                    singleLine = true,
                    label = { Text("Password") }
                )

                BasicTextField(
                    value = TextFieldValue(selectedUser?.config?.restServerIP.orEmpty()),
                    onValueChange = { selectedUser?.config?.restServerIP = it.text },
                    singleLine = true,
                    label={ Text("User Name") }
                    textStyle = androidx.compose.ui.text.TextStyle.Default
                )

                BasicTextField(
                    value = TextFieldValue(selectedUser?.config?.restServerPort.orEmpty()),
                    onValueChange = { selectedUser?.config?.restServerPort = it.text },
                    singleLine = true,

                )

                BasicTextField(
                    value = TextFieldValue(selectedUser?.config?.webSocketServerPort.orEmpty()),
                    onValueChange = { selectedUser?.config?.webSocketServerPort = it.text },
                    singleLine = true,
                    Text(text =   "Socket Port" )
                )

                Switch(
                    checked = useTurn,
                    onCheckedChange = { useTurn = it },
                    modifier = Modifier.align(Alignment.Start)
                )

                Button(
                    onClick = {
                        progressBarVisible = true
                        selectedUser?.let { viewModel.setConfiguration(it) }
                        viewModel.register()
                        viewModel.setSharedPrefs(selectedUser, useTurn)
                    },
                    enabled = accountsState?.isNullOrEmpty() == true
                ) {
                    Text("Login")
                }

                if (progressBarVisible) {
                    LinearProgressIndicator(Modifier.padding(top = 16.dp))
                }
            }

            if (accountsState.isNullOrEmpty()) {
                Text(
                    "No accounts found.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                DropdownMenu(
                    expanded = remember { mutableStateOf(false) },
                    onDismissRequest = { /* Dismiss the menu */ },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    accountsState?.forEachIndexed { index, account ->
                        DropdownMenuItem(onClick = {
                            selectedUser = account
                            useTurn = account.useTurn
                        }) {
                            Text(account.device_user)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getAccountsFirebase()
    }

    LaunchedEffect(accountsState) {
        if (!accountsState.isNullOrEmpty()) {
            selectedUser = accountsState[0]
            accountNames = accountsState.map { it.device_user }
        }
    }

    LaunchedEffect(selectedUser) {
        selectedUser?.let { setUIValues(it) }
    }

    // Observer the registration state changes
    viewModel.registrationSuccess.observe(viewLifecycleOwner) {
        progressBarVisible = false
        val bundle = Bundle()
        bundle.putParcelable("accountsData", selectedUser)
        navController.navigate(R.id.mainFragment, bundle)
        Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
    }

    viewModel.registrationFail.observe(viewLifecycleOwner) {
        progressBarVisible = false
        Toast.makeText(context, "Registration Failed: $it", Toast.LENGTH_SHORT).show()
    }

    viewModel.registrationDropped.observe(viewLifecycleOwner) {
        progressBarVisible = false
        Toast.makeText(context, "Registration Dropped: $it", Toast.LENGTH_SHORT).show()
    }

    viewModel.notificationStateChanged.observe(viewLifecycleOwner) {
        progressBarVisible = false
        Toast.makeText(context, "Notification State Changed: $it", Toast.LENGTH_SHORT).show()
    }
}
