package com.orioninc.talktome.models

data class PlatformModel(
    val id: Int,
    val name: String,
    val turnAddresses : List<String>,
    val accounts : List<AccountsData>
)
