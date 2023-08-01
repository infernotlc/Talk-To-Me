package com.orioninc.talktome.repository

import com.orioninc.talktome.models.Accounts


interface IAccountsRepository {
    fun getAccounts(callback : (accountList: Accounts) -> Unit)
}