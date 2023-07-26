package com.example.talk_to_me.models

data class AdvancedConfigurationsModel(
    val ringingFeedbackOption: String,
    val iceOption: Boolean,
    val orientationMode: String,
    val preferredCodecSet: Boolean,
    val audioCodecSet: List<String>,
    val videoCodecSet: List<String>,
    val logLevel: String,
    val loginType: String,
    val iceTimeout: Int,

)
