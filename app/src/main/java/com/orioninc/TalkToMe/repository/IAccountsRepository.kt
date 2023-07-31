package com.orioninc.TalkToMe.repository

import com.orioninc.TalkToMe.models.Accounts


interface IAccountsRepository {
    fun getAccounts(callback : (accountList: Accounts) -> Unit)
}