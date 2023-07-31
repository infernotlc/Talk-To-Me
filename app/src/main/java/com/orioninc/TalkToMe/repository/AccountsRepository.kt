package com.orioninc.TalkToMe.repository

import com.orioninc.TalkToMe.models.Accounts
import com.orioninc.TalkToMe.source.FirebaseAccountsDataSource


class AccountsRepository() : IAccountsRepository {

    private val accountsDataSource = FirebaseAccountsDataSource()


    private val TAG = "AccountsRepository"
    override fun getAccounts(callback: (accountList: Accounts) -> Unit) {
        accountsDataSource.getAccounts{
            callback(it)
        }
    }


}