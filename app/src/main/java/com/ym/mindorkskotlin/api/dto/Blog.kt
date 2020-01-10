package com.ym.mindorkskotlin.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Blog(
    @Expose @SerializedName("author") val author: String,
    @Expose @SerializedName("blog_url") val blogUrl: String,
    @Expose @SerializedName("img_url") val coverImgUrl: String,
    @Expose @SerializedName("published_at") val date: String,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("title") val title: String)