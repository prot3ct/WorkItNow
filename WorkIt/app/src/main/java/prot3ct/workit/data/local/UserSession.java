package prot3ct.workit.data.local;

import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import prot3ct.workit.data.local.base.UserSessionContract;

public class UserSession implements UserSessionContract{
    private final SharedPreferences sharedPreferences;

    public UserSession(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getAccessToken() {
        return sharedPreferences.getString("accessToken", null);
    }

    @Override
    public String getFullName() {
        return sharedPreferences.getString("fullName", null);
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }

    @Override
    public int getId() {
        return sharedPreferences.getInt("id", 0);
    }

    @Override
    public void setEmail(String email) {
        sharedPreferences.edit().putString("email", email).apply();
    }

    @Override
    public void setFullName(String fullName) {
        sharedPreferences.edit().putString("fullName", fullName).apply();
    }

    @Override
    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString("accessToken", accessToken).apply();
    }

    @Override
    public void setId(int id) {
        sharedPreferences.edit().putInt("id", id).apply();
    }

    @Override
    public boolean isUserLoggedIn() {
        return getEmail() != null;
    }

    @Override
    public void clearSession() {
        setId(0);
        setFullName(null);
        setEmail(null);
        setAccessToken(null);
    }
}
