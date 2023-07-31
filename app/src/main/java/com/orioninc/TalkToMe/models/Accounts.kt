package com.orioninc.TalkToMe.models

data class Accounts(
        val accountNames : ArrayList<String> ?= arrayListOf(),
        val userSetList : ArrayList<AccountsData> ?= arrayListOf()

    )

