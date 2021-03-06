package sharpenup.previous.sharpenup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Ravi on 01/06/15.
 */
public class PrefManager {
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String Event = "event";
    public static final String LOGIN = "login";
    // Shared pref file name
    private static final String PREF_NAME = "AndroidHive";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref1;
    // Editor for Shared preferences
    Editor editor;
    Editor editor1;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Constructor
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref1 = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();
        editor1 = pref1.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void setCenter(String product) {


        editor1.putString("center", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }

    public void setClass(String product) {


        editor1.putString("class", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }

    public void setDescription(String message, String product) {


        editor.putString(message, product);

        // Storing email in pref


        // commit changes
        editor.commit();
    }

    public String getDescription(String message) {
        return pref.getString(message, null);
    }

    public String getEvent() {
        return pref.getString(Event, null);
    }

    public void setEvent(String product) {


        editor.putString(Event, product);

        // Storing email in pref


        // commit changes
        editor.commit();
    }

    public String getLogin() {
        return pref.getString(LOGIN, null);
    }

    public void setLogin(String product) {


        editor1.putString(LOGIN, product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }

    public String getBatch() {
        return pref.getString("batch", null);
    }

    public void setBatch(String product) {


        editor1.putString("batch", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }

    public String getcenter() {
        return pref.getString("center", null);
    }

    public String getclass() {
        return pref.getString("class", null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logout() {
        editor1.clear();
        editor1.commit();
    }


}