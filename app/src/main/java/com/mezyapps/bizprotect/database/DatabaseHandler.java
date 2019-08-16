package com.mezyapps.bizprotect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IncomeExpense";
    private static final String TABLE_NAME = "IncomeExpenseTable";
    private static final String ID = "Id";
    private static final String DATE = "Date";
    private static final String STATUS = "status";
    private static final String INCOME_AMOUNT = "Income_Amount";
    private static final String EXPENSE_AMOUNT = "Expense_Amount";
    private static final String DESCRIPTION = "Description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE + " TEXT,"
                + INCOME_AMOUNT + " INTEGER," + EXPENSE_AMOUNT + " INTEGER," + DESCRIPTION + " TEXT," + STATUS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public boolean addIncomeExpense(String amount, String date, String status, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (status.equalsIgnoreCase("1")) {
            ContentValues valuesExpense = new ContentValues();
            valuesExpense.put(DATE, date);
            valuesExpense.put(EXPENSE_AMOUNT, amount);
            valuesExpense.put(INCOME_AMOUNT, 0);
            valuesExpense.put(STATUS, status);
            valuesExpense.put(DESCRIPTION, description);


            // Inserting Row
            long result = db.insert(TABLE_NAME, null, valuesExpense);
            if (result == -1) {
                db.close(); // Closing database connection
                return false;
            } else {
                db.close(); // Closing database connection
                return true;
            }
        } else {
            ContentValues valuesIncome = new ContentValues();
            valuesIncome.put(DATE, date);
            valuesIncome.put(EXPENSE_AMOUNT, 0);
            valuesIncome.put(INCOME_AMOUNT, amount);
            valuesIncome.put(STATUS, status);
            valuesIncome.put(DESCRIPTION, description);

            // Inserting Row
            long result = db.insert(TABLE_NAME, null, valuesIncome);
            if (result == -1) {
                db.close(); // Closing database connection
                return false;
            } else {
                db.close(); // Closing database connection
                return true;
            }
        }
    }


    public String getAllIncome(String Date) {
        String sumIncome = "0";
        String selectQuery = "SELECT  SUM(Income_Amount) FROM  IncomeExpenseTable WHERE Date='"+Date+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                sumIncome = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        // return contact list  
        return sumIncome;
    }

    public String getAllExpense(String Date) {
        String sumIncome = "0";
        String Status = "0";
        String selectQuery = "SELECT  SUM(Expense_Amount) FROM  IncomeExpenseTable WHERE Date='"+Date+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sumIncome = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        // return contact list
        return sumIncome;
    }
}
