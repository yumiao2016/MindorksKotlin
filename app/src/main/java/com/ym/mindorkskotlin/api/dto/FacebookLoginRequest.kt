package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FacebookLoginRequest(
    @Expose @SerializedName("fb_access_token") val fbAccessToken: String,
    @Expose @SerializedName("fb_user_id") val fbUserId: String
)