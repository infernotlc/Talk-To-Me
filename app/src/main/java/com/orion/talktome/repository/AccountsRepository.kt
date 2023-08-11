package com.orion.talktome.repository

import com.orion.talktome.models.Accounts
import com.orion.talktome.source.FirebaseAccountsDataSource


class AccountsRepository() : IAccountsRepository {

    private val accountsDataSource = FirebaseAccountsDataSource()


    private val TAG = "AccountsRepository"
    override fun getAccounts(callback: (accountList: Accounts) -> Unit) {
        accountsDataSource.getAccounts{
            callback(it)
        }
    }

    override fun getAccountNames(): Any {
        TODO("Not yet implemented")
    }


}