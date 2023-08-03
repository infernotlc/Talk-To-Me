package com.orion.talktome.composeui.Register.model

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orion.talktome.composeui.Register.viewModel.RegisterViewModel
import com.orion.talktome.models.AccountsData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDropDownMenu(){
    val context = LocalContext.current
    var isExpanded by remember{ mutableStateOf(false) }
    var accounts by remember {
        mutableStateOf("")
    }
    Box(
        modifier =Modifier.fillMaxSize(),

    ){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange ={isExpanded = it}
        ) {
            TextField(
                value = accounts,
                onValueChange ={} ,
                label = { Text(text =  "Select Account") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(

                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text (text ="account1" )  },
                    onClick = {
                        accounts = "account1"
                        isExpanded = false }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text (text ="account2" )  },
                    onClick = {
                        accounts = "account2"
                        isExpanded = false }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text (text ="account3" )  },
                    onClick = {
                        accounts = "account2"
                        isExpanded = false }
                )
            }
        }
    }
}

@Composable
private fun fetchAccountsFromDatabase(onUserSelected: (AccountsData) -> Unit) {
    val viewModel: RegisterViewModel = viewModel()
    val accountsDataList by viewModel.accounts.collectAsState()


}