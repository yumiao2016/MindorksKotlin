package com.ym.mindorkskotlin.model.feedback

import com.ym.mindorkskotlin.api.client.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedBackDataManager @Inject constructor(private val apiService: ApiService) {
    fun getBlogApiCall() = apiService.apiDefinition.getBlogs()

    fun getOpenSourceApiCall() = apiService.apiDefinition.getOpenResources()
}