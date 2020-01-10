package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoogleLoginRequest(
    @Expose @SerializedName("google_user_id") val googleUserId: String,
    @Expose @SerializedName("google_id_token") val idToken: String
)