package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerLoginRequest(
    @Expose @SerializedName("email") val email: String,
    @Expose @SerializedName("password") val password: String
)