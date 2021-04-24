package com.abualrub.assignmenttwoindividualv2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utils {
    public static boolean isValidString(String field)
    {
        return field.trim().isEmpty() ? false : true;
    }

    public static String isPropertyPreviouslyFilled(Context context,String tag)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(tag,null);
        return data;
    }
}
