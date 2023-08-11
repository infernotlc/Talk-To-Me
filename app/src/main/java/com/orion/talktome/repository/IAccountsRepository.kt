package com.orion.talktome.repository

import com.orion.talktome.models.Accounts


interface IAccountsRepository {
    fun getAccounts(callback : (accountList: Accounts) -> Unit)
    abstract fun getAccountNames(): Any
}