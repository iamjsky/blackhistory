package com.blackhistory;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";



    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }








    public void setCount(int count) {
        editor.putInt(KEY_COUNT, count);

        editor.commit();
    }

    public int getCount() {
        return pref.getInt(KEY_COUNT, 0);
    }



    private static final String KEY_LOGGED_IN = "key_logged_in";
    private static final String KEY_USERNUMBER = "key_usernumber";
    private static final String KEY_SWITCH = "key_switch";
    private static final String KEY_WEEKTIME = "key_weektime";
    private static final String KEY_COUNT = "key_count";

}
