package com.orioninc.talktome.repository

import com.orioninc.talktome.models.Accounts
import com.orioninc.talktome.source.FirebaseAccountsDataSource


class AccountsRepository() : IAccountsRepository {

    private val accountsDataSource = FirebaseAccountsDataSource()


    private val TAG = "AccountsRepository"
    override fun getAccounts(callback: (accountList: Accounts) -> Unit) {
        accountsDataSource.getAccounts{
            callback(it)
        }
    }


}