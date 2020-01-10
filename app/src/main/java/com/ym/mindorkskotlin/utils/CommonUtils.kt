package com.ym.mindorkskotlin.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object CommonUtils {
    @JvmStatic fun loadJSONFromAsset(context: Context, jsonFileName: String) : String{
        var inputStream: InputStream? = null

        try {
            inputStream = context.assets.open(jsonFileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            return String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException){
            return ""
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException){

            }
        }
    }

    @JvmStatic fun isEmailValid(email: String?): Boolean = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}