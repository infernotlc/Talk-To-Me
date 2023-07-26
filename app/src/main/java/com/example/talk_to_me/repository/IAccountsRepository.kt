package com.example.talk_to_me.repository

import com.example.talk_to_me.models.Accounts


interface IAccountsRepository {
    fun getAccounts(callback : (accountList: Accounts) -> Unit)
}