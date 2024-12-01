package com.saloonbooking.persistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.saloonbooking.persistence.models.BookingDAO;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "saloon_bookings.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE IF NOT EXISTS " + BookingDAO.TABLE_NAME + " (" +
                    BookingDAO.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BookingDAO.Columns.DATE + " TEXT, " +
                    BookingDAO.Columns.USER + " TEXT, " +
                    BookingDAO.Columns.SERVICES + " TEXT, " +
                    BookingDAO.Columns.TOTAL_PRICE + " INTEGER)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOKINGS); // Create the bookings table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table and recreate on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + BookingDAO.TABLE_NAME);
        onCreate(db);
    }
}
