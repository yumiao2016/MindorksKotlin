package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogResponse(
    @Expose @SerializedName("data") val data: List<Blog>?,
    @Expose @SerializedName("message") val message: String,
    @Expose @SerializedName("status_code") val statusCode: String
    )