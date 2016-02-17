package com.dawanse.dawn.meroo.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExpenseDB {

    //database name and version
    private static final int DB_VERSION = 10;
    private static final String DB_NAME = "expensedb.db";

    //table config
    //table names
    public static final String TABLE_EXPENSE = "expense_table";
    public static final String TABLE_CART = "cart_table";
    public static final String TABLE_DAILY_FRAG = "daily_frag_table";

    // expense_table fields
    public static final String KEY_ID = "_id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DATE = "enter_date";

    // cart_table fields
    public static final String CART_ID = "_id";
    public static final String CART_ITEM = "cart_item";
    public static final String CART_DATE = "cart_date";

    //daily_frag_table fields
    public static final String DAILY_FRAG_ID = "_id";
    public static final String DAILY_DATE = "daily_date";
    public static final String DAILY_TOTAL = "daily_total";

    //expense_table create statement
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DATE + " TEXT, "
            + KEY_ITEM + " TEXT, "
            + KEY_PRICE + " INTEGER"
            + ")";

    //cart_table create statement (Cart Activity)
    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " ("
            + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CART_ITEM + " TEXT, "
            + CART_DATE + " TEXT"
            + ")";

    //create table for Total Activity (Daily Fragment)
    private static final String CREATE_TABLE_DAILY_FRAG = "CREATE TABLE " + TABLE_DAILY_FRAG + " ("
            + DAILY_FRAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DAILY_DATE + " TEXT, "
            + DAILY_TOTAL + " INTEGER"
            + ")";

    private ExpenseDBHelper mExpenseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    //constructor
    public ExpenseDB(Context context) {
        mExpenseDBHelper = new ExpenseDBHelper(context);
        mSQLiteDatabase = mExpenseDBHelper.getWritableDatabase();
    }

    //entry for expense_table
    public void createEntry(String item, float price) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE, getDate());
        cv.put(KEY_ITEM, item);
        cv.put(KEY_PRICE, price);

        mSQLiteDatabase.insert(TABLE_EXPENSE, null, cv);
    }

    //current date method
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    //entry for cart_table
    public void createCart(String cartItem, String cartDate) {
        ContentValues cv = new ContentValues();
        cv.put(CART_ITEM, cartItem);
        cv.put(CART_DATE, cartDate);

        mSQLiteDatabase.insert(TABLE_CART, null, cv);
    }

    //entry for daily_frag_table
    public void createDailyFrag() {
        ContentValues cv = new ContentValues();
        cv.put(DAILY_DATE, getDate());
        cv.put(DAILY_TOTAL, getTotalTodayFragmentData());
        if (checkDailyFragmentData() == null) {
            mSQLiteDatabase.insert(TABLE_DAILY_FRAG, null, cv);
        } else {
            String q = "DELETE FROM " + TABLE_DAILY_FRAG + " WHERE " + DAILY_DATE + " = date('now', 'localtime')";
            mSQLiteDatabase.execSQL(q);
            mSQLiteDatabase.insert(TABLE_DAILY_FRAG, null, cv);
        }
    }

    //open a database
    public void open() throws SQLException {
        mSQLiteDatabase = mExpenseDBHelper.getWritableDatabase();
    }

    //close a database
    public void close() {
        mSQLiteDatabase.close();
    }


    //retrieving all data from database for table expense (MainActivity)
    public Cursor getAllData() {
        String query = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + KEY_ID + " DESC";
        return mSQLiteDatabase.rawQuery(query, null);
    }

    //retrieving all data from cart activity (CartActivity)
    public Cursor getCartData() {
        String[] cols = {CART_ID, CART_ITEM, CART_DATE};
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mSQLiteDatabase.query(TABLE_CART, cols, null, null, null, null, CART_ID + " DESC");
    }


    //retrieving today's data from TABLE_EXPENSE to show in (TodayFragment)
    public Cursor getTodayFragmentData() {
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + KEY_DATE + " = date('now', 'localtime')"
                + " ORDER BY " + KEY_ID + " DESC";
        return mSQLiteDatabase.rawQuery(query, null);

    }

    //retrieving data from TABLE_DAILY_FRAG to show in (DailyFragment)
    public Cursor getDailyFragmentData() {
        String query = "SELECT * FROM " + TABLE_DAILY_FRAG + " ORDER BY " + DAILY_FRAG_ID + " DESC";
        return mSQLiteDatabase.rawQuery(query, null);
    }

    //check DailyFragment table
    public Cursor checkDailyFragmentData() {
        String query = "SELECT * FROM " + TABLE_DAILY_FRAG + " WHERE " + DAILY_DATE + " = date('now', 'localtime')";
        return mSQLiteDatabase.rawQuery(query, null);
    }

    //showing sum of price of today's data from TABLE_EXPENSE (TodayFragment)
    public int getTotalTodayFragmentData() {
        int sum = 0;
        String query = "SELECT * FROM " + TABLE_EXPENSE
                + " WHERE " + KEY_DATE + " = date('now', 'localtime')";
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sum += cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
        }
        cursor.close();

        return sum;
    }

    //showing data monthly from TABLE_EXPENSE (MonthlyFragment)
    public int getTotalMonthlyFragmentData() {
        int sum = 0;
        String query = "SELECT * FROM " + TABLE_EXPENSE;
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sum += cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
        }
        cursor.close();
        return sum;
    }

    //for deleting a row of table expense
    public void delete(int id) {
        //delete from table_expense
        mSQLiteDatabase.delete(TABLE_EXPENSE, KEY_ID + " = " + id, null);
    }

    //for deleting row of table cart
    public void deleteCartRow(int item_id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mSQLiteDatabase.delete(TABLE_CART, CART_ID + " = " + item_id, null);
        close();
    }

    //class to perform database related operation
    public class ExpenseDBHelper extends SQLiteOpenHelper {

        public ExpenseDBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //expense_table
            db.execSQL(CREATE_TABLE_EXPENSE);
            //cart_table
            db.execSQL(CREATE_TABLE_CART);
            //daily_frag_table
            db.execSQL(CREATE_TABLE_DAILY_FRAG);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //expense_table onUpgrade
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
            //cart_table onUpgrade
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
            //daily_frag_table onUpgrade
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_FRAG);

            onCreate(db);

        }

    }

    public ArrayList<String> queryXData(){
        ArrayList<String> xNewData = new ArrayList<String>();
        String query = "SELECT " + DAILY_DATE + " FROM " + TABLE_DAILY_FRAG + " ORDER BY " + DAILY_FRAG_ID + " DESC";
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            xNewData.add(cursor.getString(cursor.getColumnIndex(DAILY_DATE)));
        }
        cursor.close();
        return xNewData;
    }

    public ArrayList<Float> queryYData(){
        ArrayList<Float> yNewData = new ArrayList<Float>();
        String query = "SELECT " + DAILY_TOTAL + " FROM " + TABLE_DAILY_FRAG + " ORDER BY " + DAILY_FRAG_ID + " DESC";
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            yNewData.add(cursor.getFloat(cursor.getColumnIndex(DAILY_TOTAL)));
        }
        cursor.close();
        return yNewData;
    }

}

