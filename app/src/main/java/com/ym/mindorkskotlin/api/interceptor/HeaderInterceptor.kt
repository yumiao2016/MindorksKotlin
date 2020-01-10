package com.ym.mindorkskotlin.api.interceptor

import com.ym.mindorkskotlin.api.header.CustomHeaders
import com.ym.mindorkskotlin.config.AppConfig
import com.ym.mindorkskotlin.model.prefs.SharedPreferencesDataManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val sharedPreferencesDataManager : SharedPreferencesDataManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader(CustomHeaders.API_KEY_HEADER_NAME, AppConfig.API_KEY)
        if(tokenRequired(request)){
            requestBuilder.addHeader(CustomHeaders.TOKEN_HEADER_NAME, sharedPreferencesDataManager.token)
            requestBuilder.addHeader(CustomHeaders.USER_ID_HEADER_NAME, "${sharedPreferencesDataManager.userId}")
        }
        requestBuilder.removeHeader(CustomHeaders.TOKEN_REQUIRED_HEADER_NAME)

        return chain.proceed(requestBuilder.build())
    }

    private fun tokenRequired(request: Request) : Boolean{
        val tokenRequired = request.header(CustomHeaders.TOKEN_REQUIRED_HEADER_NAME)
        return CustomHeaders.IGNORED != tokenRequired
    }
}