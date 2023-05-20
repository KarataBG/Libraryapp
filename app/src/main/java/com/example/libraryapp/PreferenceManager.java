package com.example.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class PreferenceManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_ID = "id";
    private static final ArrayList<Integer> KEY_FreeToTakeBooks = new ArrayList<>();

    private static final ArrayList<Integer> KEY_UserTakenBooks = new ArrayList<>();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    //ID
    public static void setID(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_ID, String.valueOf(id));
        editor.apply();
    }

    public static String getID(Context context) {
        return getSharedPreferences(context).getString(KEY_ID, "");
    }

    public static void clearID(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_ID);
        editor.apply();
    }


    //ID на книги за взимане
    public static void setFreeToTakeBooks(int id) {
        KEY_FreeToTakeBooks.add(id);
    }

    public static ArrayList<Integer> getFreeToTakeBooks() {
        return KEY_FreeToTakeBooks;
    }

    public static void clearFreeToTakeBooks() {
        KEY_FreeToTakeBooks.clear();
    }
    public static void removeFreeToTakeBooks(int object){
        KEY_FreeToTakeBooks.remove((Object) object);
    }

    //ID на книги за връщане
    public static void setUserTakenBooks(int id) {
        KEY_UserTakenBooks.add(id);
    }

    public static ArrayList<Integer> getUserTakenBooks() {
        return KEY_UserTakenBooks;
    }

    public static void clearUserTakenBooks() {
        KEY_UserTakenBooks.clear();
    }
    public static void removeUserTakenBooks(int object){
        KEY_UserTakenBooks.remove((Object) object);
    }
}
