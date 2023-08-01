package com.orion.talktome.models

data class Accounts(
        val accountNames : ArrayList<String> ?= arrayListOf(),
        val userSetList : ArrayList<AccountsData> ?= arrayListOf()

    )

