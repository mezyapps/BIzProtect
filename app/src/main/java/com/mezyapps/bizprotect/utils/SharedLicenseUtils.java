package com.mezyapps.bizprotect.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedLicenseUtils {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static String getLicenseSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LICENSE_PREFERENCE, mContext.MODE_PRIVATE);
        return preferences.getString(ConstantFields.IS_LICENSE_APPROVE, "");
    }

    public static void putLicenseSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LICENSE_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.IS_LICENSE_APPROVE, "yes");
        editor.commit();
    }




}
