package com.example.talk_to_me.models

data class Accounts(
        val accountNames : ArrayList<String> ?= arrayListOf(),
        val userSetList : ArrayList<AccountsData> ?= arrayListOf()

    )

