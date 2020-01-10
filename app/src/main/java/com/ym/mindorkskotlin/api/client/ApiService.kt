package com.ym.mindorkskotlin.api.client

import com.ym.mindorkskotlin.api.interceptor.HeaderInterceptor
import com.ym.mindorkskotlin.config.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(private val headerInterceptor: HeaderInterceptor){
    val apiDefinition by lazy {
        initRetrofit().create(ApiDefinition::class.java)
    }

    private fun initRetrofit() = Retrofit.Builder()
        .baseUrl(AppConfig.BASE_URL)
        .client(initOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun initOkHttpClient() = OkHttpClient.Builder().addInterceptor(headerInterceptor).build()
}