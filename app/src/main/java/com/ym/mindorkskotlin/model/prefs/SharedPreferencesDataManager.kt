package com.ym.mindorkskotlin.model.prefs

import android.content.Context
import com.ym.mindorkskotlin.model.login.entity.LoggedInMode
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Singleton
class SharedPreferencesDataManager @Inject constructor(private val context: Context){
    companion object{
        private const val PREF_NAME = "mindorks_pref"

        const val DEFAULT_STRING = ""
        const val DEFAULT_LONG = -1L

        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
    }

    private val mPrefs by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    var token by SharedPreferenceDelegate(PREF_KEY_ACCESS_TOKEN, DEFAULT_STRING)
    var userEmail by SharedPreferenceDelegate(PREF_KEY_CURRENT_USER_EMAIL, DEFAULT_STRING)
    var userId by SharedPreferenceDelegate(PREF_KEY_CURRENT_USER_ID, DEFAULT_LONG)
    var userName by SharedPreferenceDelegate(PREF_KEY_CURRENT_USER_NAME, DEFAULT_STRING)
    var userProfileUrl by SharedPreferenceDelegate(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, DEFAULT_STRING)
    var userLoginMode by SharedPreferenceDelegate(PREF_KEY_USER_LOGGED_IN_MODE, LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type)

    inner class SharedPreferenceDelegate<T>(private val key: String, private val default: T) : ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            putSharedPreference(key, value)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return getSharedPreference(key, default)
        }

        private fun getSharedPreference(key: String, default: T): T {
            return when(default){
                is String -> mPrefs.getString(key, default) as T
                is Int -> mPrefs.getInt(key, default) as T
                is Long -> mPrefs.getLong(key, default) as T
                is Boolean -> mPrefs.getBoolean(key, default) as T
                is Float -> mPrefs.getFloat(key, default) as T
                else -> throw UnsupportedOperationException()
            }
        }

        private fun putSharedPreference(key: String, value: T) {
            when(value){
                is String -> mPrefs.edit().putString(key, value).apply()
                is Int -> mPrefs.edit().putInt(key, value).apply()
                is Long -> mPrefs.edit().putLong(key, value).apply()
                is Boolean -> mPrefs.edit().putBoolean(key, value).apply()
                is Float -> mPrefs.edit().putFloat(key, value).apply()
            }
        }
    }
}