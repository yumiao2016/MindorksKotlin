package com.ym.mindorkskotlin.api.header

object CustomHeaders {
    const val TOKEN_REQUIRED_HEADER_NAME = "X-token-required"
    const val IGNORED = "false"
    const val HEADER_IGNORE_TOKEN = "$TOKEN_REQUIRED_HEADER_NAME: $IGNORED"

    const val API_KEY_HEADER_NAME = "api_key"
    const val TOKEN_HEADER_NAME = "access_token"
    const val USER_ID_HEADER_NAME = "user_id"
}