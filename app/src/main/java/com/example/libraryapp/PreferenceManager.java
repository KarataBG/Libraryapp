package com.example.libraryapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class PreferenceManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_ID = "id";

    private static final String KEY_LineLength = "lineLength";
    private static final ArrayList<Integer> KEY_FreeToTakeBooks = new ArrayList<>();

    private static final ArrayList<Integer> KEY_UserTakenBooks = new ArrayList<>();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    //ID на текущия потребител
    public static void setID(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(KEY_ID, id);
        editor.apply();
    }

    public static int getID(Context context) {
        return getSharedPreferences(context).getInt(KEY_ID, 0);
    }

    public static void clearID(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_ID);
        editor.apply();
    }


    //ID на свободните книги за взимане
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

    //ID на взетите книги от потребителя за връщане
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

    public static void setLineLength(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(KEY_LineLength, id);
        editor.apply();
    }

    public static int getLineLength(Context context) {
        return getSharedPreferences(context).getInt(KEY_LineLength, 0);
    }

    public static void clearLineLength(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_LineLength);
        editor.apply();
    }
}
