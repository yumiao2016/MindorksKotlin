package com.ym.mindorkskotlin.model.login

import com.ym.mindorkskotlin.api.client.ApiService
import com.ym.mindorkskotlin.api.dto.*
import com.ym.mindorkskotlin.model.db.AppDbDataManager
import com.ym.mindorkskotlin.model.login.entity.LoggedInMode
import com.ym.mindorkskotlin.model.prefs.SharedPreferencesDataManager
import com.ym.mindorkskotlin.model.prefs.SharedPreferencesDataManager.Companion.DEFAULT_LONG
import com.ym.mindorkskotlin.model.prefs.SharedPreferencesDataManager.Companion.DEFAULT_STRING
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataManager @Inject constructor(private val preferencesDataManager: SharedPreferencesDataManager,
                                           private val apiService: ApiService,
                                           private val dbDataManager: AppDbDataManager){

    fun getCurrentUserLoggedInMode() = preferencesDataManager.userLoginMode

    fun doFacebookLoginApiCall(request: FacebookLoginRequest) = apiService.apiDefinition.loginWithFacebook(request)

    fun doGoogleLoginApiCall(request: GoogleLoginRequest) = apiService.apiDefinition.loginWithGoogle(request)

    fun doServerLoginApiCall(request: ServerLoginRequest) = apiService.apiDefinition.loginWithServer(request)

    fun doLogoutApiCall(): Observable<LogoutResponse> = apiService.apiDefinition.logout()

    fun setUserAsLoggedOut() {
        setAccessToken(DEFAULT_STRING)
        setCurrentUserId(DEFAULT_LONG)
        setCurrentUserLoggedInMode(LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)
        setCurrentUserName(DEFAULT_STRING)
        setCurrentUserEmail(DEFAULT_STRING)
        setCurrentUserProfilePicUrl(DEFAULT_STRING)
    }

    fun updateUserInfo(user: LoginResponse, loggedInMode: LoggedInMode) {
        setAccessToken(user.accessToken)
        setCurrentUserId(user.userId)
        setCurrentUserLoggedInMode(loggedInMode)
        setCurrentUserName(user.userName)
        setCurrentUserEmail(user.userEmail)
        setCurrentUserProfilePicUrl(user.profilePicUrl)
    }

    private fun setAccessToken(accessToken: String) {
        preferencesDataManager.token = accessToken
    }

    private fun setCurrentUserId(userId: Long) {
        preferencesDataManager.userId = userId
    }

    private fun setCurrentUserLoggedInMode(mode: LoggedInMode) {
        preferencesDataManager.userLoginMode = mode.type
    }

    private fun setCurrentUserName(userName: String) {
        preferencesDataManager.userName = userName
    }

    private fun setCurrentUserEmail(email: String){
        preferencesDataManager.userEmail = email
    }

    private fun setCurrentUserProfilePicUrl(profilePicPath: String?){
        profilePicPath?.apply { preferencesDataManager.userProfileUrl = this }
    }

    fun getCurrentUserEmail() = preferencesDataManager.userEmail

    fun getCurrentUserName() = preferencesDataManager.userName

    fun getCurrentUserProfilePicUrl() = preferencesDataManager.userProfileUrl
}