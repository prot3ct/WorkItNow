package prot3ct.workit.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class UserSession(context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var id: Int
        get() = sharedPreferences.getInt("id", 0)
        set(newId) {
            sharedPreferences.edit().putInt("id", newId).apply()
        }

    var accessToken: String?
        get() = sharedPreferences.getString("accessToken", null)
        set(accessToken) {
            sharedPreferences.edit().putString("accessToken", accessToken).apply()
        }

    var fullName: String?
        get() = sharedPreferences.getString("fullName", null)
        set(fullName) {
            sharedPreferences.edit().putString("fullName", fullName).apply()
        }

    var email: String?
        get() = sharedPreferences.getString("email", null)
        set(email) {
            sharedPreferences.edit().putString("email", email).apply()
        }

    var loggedInUserId: Int
        get() = sharedPreferences.getInt("id", 0)
        set(id) {
            sharedPreferences.edit().putInt("id", id).apply()
        }

    val isLoggedIn: Boolean
        get() = email != null

    fun clearSession() {
        id = 0
        fullName = null
        email = null
        accessToken = null
    }
}