package id.wth.celenganmandiri.utils;

import android.content.Context;
import android.content.SharedPreferences;

import id.wth.celenganmandiri.config.Config;
import id.wth.celenganmandiri.model.User;

/**
 * Created by adminmc on 15/03/17.
 */

public class PreferenceManager {

    private String TAG = PreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref, sPref, cPref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor, sEditor, cEditor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_APPS = "kongkon_mitra";
    private static final String PREF_USERNAME = "username";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PHONE = "phone";
    private static final String KEY_USER_PT = "pt";
    private static final String KEY_USER_ALAMAT = "alamat";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_SESSION = "session";
    private static final String KEY_USER_PHOTO = "photo";
    private static final String KEY_USER_RATING = "rating";
    private static final String KEY_USER_LNG = "longitude";
    private static final String KEY_USER_LAT = "latitude";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String KEY_JWT = "jwt";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_USERNAME, PRIVATE_MODE);
        editor = pref.edit();

        sPref = _context.getSharedPreferences(Config.KEY_SESSION, PRIVATE_MODE);
        sEditor = sPref.edit();

        cPref = _context.getSharedPreferences(PREF_APPS, PRIVATE_MODE);
        cEditor = cPref.edit();
    }

    public void setPreference(String key, String value) {
        cEditor.putString(key, value);
        cEditor.commit();
    }

    public String getPreference(String key) {
        if (cPref.getString(key, null) !=null) {
            return cPref.getString(key, null);
        }
        return null;
    }

    public void storeSession(String session) {
        sEditor.putString(KEY_USER_SESSION, session);
        sEditor.commit();
    }

    public String getSession() {
        if (sPref.getString(KEY_USER_SESSION, null) !=null) {
            return sPref.getString(KEY_USER_SESSION, null);
        }
        return null;
    }

    public void storeUser(User user) {
        editor.putString(PREF_USERNAME, user.getUsername());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_PT, user.getPt());
        editor.putString(KEY_USER_NAME, user.getNama());
        editor.putString(KEY_USER_ALAMAT, user.getAlamat());
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_PHOTO, user.getPhoto());
        editor.putString(KEY_USER_RATING, user.getRating());
        editor.putString(KEY_USER_LNG, user.getLongitude());
        editor.putString(KEY_USER_LAT, user.getLatitude());
        editor.commit();
    }

    public User getUser() {
        if (pref.getString(KEY_USER_NAME, null) != null) {

            String nama, email, phone, pt, alamat, username, id, photo, rating, lng, lat;

            nama = pref.getString(KEY_USER_NAME, null);
            email = pref.getString(KEY_USER_EMAIL, null);
            username = pref.getString(PREF_USERNAME, null);
            pt = pref.getString(KEY_USER_PT, null);
            alamat = pref.getString(KEY_USER_ALAMAT, null);
            phone = pref.getString(KEY_USER_PHONE, null);
            id = pref.getString(KEY_USER_ID, null);
            photo = pref.getString(KEY_USER_PHOTO, null);
            rating = pref.getString(KEY_USER_RATING, null);
            lng = pref.getString(KEY_USER_LNG, null);
            lat = pref.getString(KEY_USER_LAT, null);
            User user = new User(nama, email, phone, pt, alamat, username, id, photo, rating, lng, lat);
            return user;
        }

        return null;
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void clear() {
        editor.clear();
        editor.commit();
        sEditor.clear();
        sEditor.commit();
        cEditor.clear();
        cEditor.commit();
    }
}
