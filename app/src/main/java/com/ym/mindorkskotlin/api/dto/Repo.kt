package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repo(
    @Expose @SerializedName("project_url") val projectUrl: String,
    @Expose @SerializedName("img_url") val coverImgUrl: String,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("title") val title: String)