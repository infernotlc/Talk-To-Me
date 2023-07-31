package com.orioninc.TalkToMe.models

data class PlatformModel(
    val id: Int,
    val name: String,
    val turnAddresses : List<String>,
    val accounts : List<AccountsData>
)
