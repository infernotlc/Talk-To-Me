package com.example.talk_to_me.repository

import com.example.talk_to_me.models.Accounts
import com.example.talk_to_me.source.FirebaseAccountsDataSource


class AccountsRepository() : IAccountsRepository {

    private val accountsDataSource = FirebaseAccountsDataSource()


    private val TAG = "AccountsRepository"
    override fun getAccounts(callback: (accountList: Accounts) -> Unit) {
        accountsDataSource.getAccounts{
            callback(it)
        }
    }


}