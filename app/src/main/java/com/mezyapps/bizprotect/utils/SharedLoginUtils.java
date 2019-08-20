package com.mezyapps.bizprotect.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mezyapps.bizprotect.model.ClientProfileModel;

import java.util.ArrayList;

public class SharedLoginUtils {


    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static String getLoginSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        return preferences.getString(ConstantFields.IS_LOGIN, "");
    }

    public static void putLoginSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.IS_LOGIN, "true");
        editor.commit();
    }

    public static void removeLoginSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.IS_LOGIN, "false");
        editor.commit();
    }


    public static void addUserUtils(Context mContext, ArrayList<ClientProfileModel> clientProfileModelArrayList) {

        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.SharedLoginConstant.ID,clientProfileModelArrayList.get(0).getClient_id());
        editor.putString(ConstantFields.SharedLoginConstant.NAME, clientProfileModelArrayList.get(0).getClient_name());
        editor.putString(ConstantFields.SharedLoginConstant.COMPANY_NAME, clientProfileModelArrayList.get(0).getCompany_name());
        editor.putString(ConstantFields.SharedLoginConstant.ADDRESS, clientProfileModelArrayList.get(0).getAddress());
        editor.putString(ConstantFields.SharedLoginConstant.PHONE, clientProfileModelArrayList.get(0).getMobile_no());
        editor.putString(ConstantFields.SharedLoginConstant.EMAIL, clientProfileModelArrayList.get(0).getEmail());
        editor.putString(ConstantFields.SharedLoginConstant.AADHAR_NUMBER, clientProfileModelArrayList.get(0).getAadhar_no());
        editor.putString(ConstantFields.SharedLoginConstant.PAN_NUMBER, clientProfileModelArrayList.get(0).getPan_no());
        editor.putString(ConstantFields.SharedLoginConstant.GST_NUMBER, clientProfileModelArrayList.get(0).getGst_no());
        editor.commit();
    }

    public static ArrayList<ClientProfileModel> getUserDetails(Context mContext) {
        ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
        ClientProfileModel clientProfileModel = new ClientProfileModel();
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        clientProfileModel.setClient_id(preferences.getString(ConstantFields.SharedLoginConstant.ID, ""));
        clientProfileModel.setClient_name(preferences.getString(ConstantFields.SharedLoginConstant.NAME, ""));
        clientProfileModel.setCompany_name(preferences.getString(ConstantFields.SharedLoginConstant.COMPANY_NAME, ""));
        clientProfileModel.setAddress(preferences.getString(ConstantFields.SharedLoginConstant.ADDRESS, ""));
        clientProfileModel.setMobile_no(preferences.getString(ConstantFields.SharedLoginConstant.PHONE, ""));
        clientProfileModel.setEmail(preferences.getString(ConstantFields.SharedLoginConstant.EMAIL, ""));
        clientProfileModel.setAadhar_no(preferences.getString(ConstantFields.SharedLoginConstant.AADHAR_NUMBER, ""));
        clientProfileModel.setGst_no(preferences.getString(ConstantFields.SharedLoginConstant.GST_NUMBER, ""));
        clientProfileModel.setPan_no(preferences.getString(ConstantFields.SharedLoginConstant.PAN_NUMBER, ""));
        clientProfileModelArrayList.add(clientProfileModel);
        return clientProfileModelArrayList;
    }

    public static void putCurrentDate(Context mContext,String date) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.CURRENT_DATE_CHECK, date);
        editor.commit();
    }
    public static String getCurrentDate(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        return preferences.getString(ConstantFields.CURRENT_DATE_CHECK, "");
    }


    public static void putBackUpDateTime(Context mContext,String dateTime) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.BACK_UP_DATE_TIME, dateTime);
        editor.commit();
    }
    public static String getBackupDateTime(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        return preferences.getString(ConstantFields.BACK_UP_DATE_TIME, "");
    }


    public static String getBackUpStatus(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        return preferences.getString(ConstantFields.BACK_UP_STATUS, "");
    }

    public static void putBackUpStatus(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.BACK_UP_STATUS, "true");
        editor.commit();
    }

    public static void removeBackUpStatus(Context mContext) {
        preferences = mContext.getSharedPreferences(ConstantFields.LOGIN_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ConstantFields.BACK_UP_STATUS, "false");
        editor.commit();
    }


}
