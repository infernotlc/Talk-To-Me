package com.orioninc.TalkToMe.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IceServers(
    var servers: List<String>
) : Parcelable {
    constructor() : this(emptyList())
}

