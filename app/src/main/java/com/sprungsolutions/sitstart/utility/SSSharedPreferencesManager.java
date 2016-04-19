package com.sprungsolutions.sitstart.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SSSharedPreferencesManager {

    private static final String SHARED_PREFS = "sit_or_start_prefs";
    private SharedPreferences mSharedPreferencesFile;
    private static SSSharedPreferencesManager mHomeSharedPreferences = null;

    public static final String START_OR_SIT_SET_ID = "set_id";
    public static final String START_OR_SIT_TUTORIAL_DONE = "tutorial_done";
    public static final String START_OR_SIT_UPDATE_DONE = "update_done";


    private SSSharedPreferencesManager(Context context) {
        mSharedPreferencesFile = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static synchronized SSSharedPreferencesManager getInstance(Context context) {

        if (mHomeSharedPreferences == null) {
            mHomeSharedPreferences = new SSSharedPreferencesManager(context);
        }

        return mHomeSharedPreferences;
    }

    public void putBooleanSharedPreferences(String key, boolean keyValue) {

        if (mSharedPreferencesFile != null) {
            Editor editor = mSharedPreferencesFile.edit();
            editor.putBoolean(key, keyValue);
            editor.commit();
        }
    }

    public void putStringSharedPreferences(String key, String keyValue) {

        if (mSharedPreferencesFile != null) {
            Editor editor = mSharedPreferencesFile.edit();
            editor.putString(key, keyValue);
            editor.commit();
        }
    }

    public void putLongSharedPreferences(String key, long keyValue) {

        if (mSharedPreferencesFile != null) {
            Editor editor = mSharedPreferencesFile.edit();
            editor.putLong(key, keyValue);
            editor.commit();
        }
    }

    public long getLongSharedPreferences(String key, long defValue) {

        if (mSharedPreferencesFile != null)
            return mSharedPreferencesFile.getLong(key, defValue);

        return defValue;
    }


    public void putIntSharedPreferences(String key, int keyValue) {

        if (mSharedPreferencesFile != null) {
            Editor editor = mSharedPreferencesFile.edit();
            editor.putInt(key, keyValue);
            editor.commit();
        }
    }

    public String getStringSharedPreferences(String key, String defValue) {

        if (mSharedPreferencesFile != null)
            return mSharedPreferencesFile.getString(key, defValue);

        return defValue;
    }

    public boolean getBooleanSharedPreferences(String key, boolean defValue) {

        if (mSharedPreferencesFile != null)
            return mSharedPreferencesFile.getBoolean(key, defValue);

        return defValue;
    }

    public int getIntSharedPreferences(String key, int defValue) {

        if (mSharedPreferencesFile != null)
            return mSharedPreferencesFile.getInt(key, defValue);

        return defValue;

    }

    public boolean contains(String key) {

        if (mSharedPreferencesFile != null)
            return mSharedPreferencesFile.contains(key);

        return false;

    }

    public void removeKey(String keyToRemove) {

        if (mSharedPreferencesFile != null) {

            Editor editor = mSharedPreferencesFile.edit();
            editor.remove(keyToRemove);
            editor.commit();
        }
    }

    public void deleteSharedPrefrence() {

        if (mSharedPreferencesFile != null) {

            mSharedPreferencesFile.edit().clear().commit();
        }
    }

    public void storeID(Context context, List contacts) {
// used for store arrayList in json format
        Editor editor;

        editor = mSharedPreferencesFile.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(contacts);
        editor.putString(START_OR_SIT_SET_ID, jsonFavorites);
        editor.commit();
    }

    public ArrayList loadID(Context context) {
// used for retrieving arraylist from json formatted string

        List favorites;

        if (mSharedPreferencesFile.contains(START_OR_SIT_SET_ID)) {
            String jsonFavorites = mSharedPreferencesFile.getString(START_OR_SIT_SET_ID, null);
            Gson gson = new Gson();
            String[] contactItems = gson.fromJson(jsonFavorites, String[].class);
            favorites = Arrays.asList(contactItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }

    public void addID(Context context, String string) {
        List contacts = loadID(context);
        if (contacts == null)
            contacts = new ArrayList();

        Set<String> set = new HashSet<String>(contacts);
        set.add(string);
        List<String> list = new ArrayList<String>(set);
        storeID(context, list);
    }

    public void removeID(Context context, String string) {
        ArrayList contacts = loadID(context);
        if (contacts != null) {
            contacts.remove(string);
            storeID(context, contacts);
        }
    }
}
