package com.mezyapps.bizprotect.utils;

public class ConstantFields {

    public static final String LICENSE_PREFERENCE="license_preference";
    public static final String LOGIN_PREFERENCE="Login_preference";
    public static final String IS_LOGIN="IS_LOGIN";
    public static final String IS_LICENSE_APPROVE="IS_APPROVE";
    public static final String DEVICE_ID="DEVICE_ID";
    public static final String CURRENT_DATE_CHECK="CURRENT_DATE";
    public static final String BACK_UP_DATE_TIME="BACK_UP_DATE_TIME";
    public static final String BACK_UP_STATUS="BACK_UP_STATUS";

    public static class  SharedLoginConstant
    {
        public static final String ID="ID";
        public static final String NAME="NAME";
        public static final String COMPANY_NAME="COMPANY_NAME";
        public static final String ADDRESS="ADDRESS";
        public static final String PHONE="PHONE";
        public static final String EMAIL="EMAIL";
        public static final String AADHAR_NUMBER="AADHAR_NUMBER";
        public static final String PAN_NUMBER="PAN_NUMBER";
        public static final String GST_NUMBER="GST_NUMBER";
    }
    public static class CommonConstant
    {
      public static String sync_ok="1";
      public static String sync_failed="0";
    }


    public static class DatabaseConstant {
        public  static int DATABASE_VERSION = 1;
        public static String DATABASE_NAME = "IncomeExpense";
        public static String TABLE_NAME = "IncomeExpenseTable";
        public static String ID = "Id";
        public static String DATE = "Date";
        public static String STATUS = "status";
        public static String INCOME_AMOUNT = "Income_Amount";
        public static String EXPENSE_AMOUNT = "Expense_Amount";
        public static String DESCRIPTION = "Description";
        public static String SYNC_STATUS = "SYNC_STATUS";
        public static String UI_UPDATE_BROADCAST = "com.mezyapps.bizprotect.uiupdatebroadcast";
    }

}
