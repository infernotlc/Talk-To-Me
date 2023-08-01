package com.orion.talktome.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class AccountsData(
    val ICEServers : IceServers,
    val config : Configurations,
    var default_domain : String,
    var device_pass : String,
    var device_user : String,
    var pushServerURL : String,
    var useTurn : Boolean,



    ) : Parcelable {
    constructor() : this(
        IceServers(arrayListOf()),
        Configurations(3,"","","",""),"","","","",true)
}
