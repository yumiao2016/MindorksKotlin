package com.ym.mindorkskotlin.api.client

import com.ym.mindorkskotlin.api.dto.*
import com.ym.mindorkskotlin.api.header.CustomHeaders
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiDefinition {
    @Headers(CustomHeaders.HEADER_IGNORE_TOKEN)
    @POST("v2/588d15d3100000ae072d2944")
    fun loginWithFacebook(@Body body: FacebookLoginRequest) : Observable<LoginResponse>

    @Headers(CustomHeaders.HEADER_IGNORE_TOKEN)
    @POST("v2/588d14f4100000a9072d2943")
    fun loginWithGoogle(@Body body: GoogleLoginRequest) : Observable<LoginResponse>

    @Headers(CustomHeaders.HEADER_IGNORE_TOKEN)
    @POST("v2/588d15f5100000a8072d2945")
    fun loginWithServer(@Body body: ServerLoginRequest) : Observable<LoginResponse>

    @POST("v2/588d161c100000a9072d2946")
    fun logout() : Observable<LogoutResponse>

    @GET("v2/5926ce9d11000096006ccb30")
    fun getBlogs() : Observable<BlogResponse>

    @GET("v2/5926c34212000035026871cd")
    fun getOpenResources() : Observable<OpenSourceResponse>
}