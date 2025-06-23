package local

import android.content.SharedPreferences
import androidx.core.content.edit
import constant.TokenConstant.KEY_ACCESS_TOKEN
import constant.TokenConstant.KEY_SESSION_ID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveAccessToken(token: String) {
        sharedPreferences.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null).orEmpty()
    }

    fun clearToken() {
        sharedPreferences.edit { remove(KEY_ACCESS_TOKEN) }
    }

    fun saveSessionId(token: String) {
        sharedPreferences.edit { putString(KEY_SESSION_ID, token) }
    }

    fun getSessionId(): String {
        return sharedPreferences.getString(KEY_SESSION_ID, null).orEmpty()
    }

    fun clearSessionId() {
        sharedPreferences.edit { remove(KEY_SESSION_ID) }
    }
}