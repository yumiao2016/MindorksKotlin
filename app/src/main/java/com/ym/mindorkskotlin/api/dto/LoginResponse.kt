package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose @SerializedName("access_token") val accessToken: String,
    @Expose @SerializedName("profile_pic_url") val profilePicUrl: String?,
    @Expose @SerializedName("message") val message: String,
    @Expose @SerializedName("status_code") val statusCode: String,
    @Expose @SerializedName("email") val userEmail: String,
    @Expose @SerializedName("user_id") val userId: Long,
    @Expose @SerializedName("user_name") val userName: String
    )