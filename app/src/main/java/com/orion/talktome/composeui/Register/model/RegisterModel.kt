package com.orion.talktome.composeui.Register.model

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orion.talktome.composeui.Register.viewModel.RegisterViewModel
import com.orion.talktome.models.AccountsData
import com.orion.talktome.composeui.Register.view.RegisterCompose


@Composable
fun AccountDropDownMenu(){
    val context = LocalContext.current
    var expanded by remember{ mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
    ){
        IconButton(onClick = { expanded =! expanded }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "or")
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {expanded = false}
        )
    {
        DropdownMenuItem(
            text = { "load" },
            onClick = { Toast.makeText(context,"load",Toast.LENGTH_SHORT).show() }
        )
        DropdownMenuItem(
            text = { "save" },
            onClick = { Toast.makeText(context,"save",Toast.LENGTH_SHORT).show() }
        )
    }


}

@Composable
private fun fetchAccountsFromDatabase(onUserSelected: (AccountsData) -> Unit) {
    val viewModel: RegisterViewModel = viewModel()
    val accountsDataList by viewModel.accounts.collectAsState()


}