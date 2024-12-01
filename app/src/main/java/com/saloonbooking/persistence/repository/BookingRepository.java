package com.saloonbooking.persistence.repository;

import android.content.ContentValues;
import android.content.Context;
import com.saloonbooking.persistence.models.BookingDAO;
import com.saloonbooking.persistence.sqlite.SQLiteHelper;
import com.saloonbooking.ui.home.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final SQLiteHelper dbHelper;

    public BookingRepository(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public List<Booking> getAllBookings() {
        var db = dbHelper.getReadableDatabase();
        var bookings = new ArrayList<Booking>();

        var cursor = db.query(
                BookingDAO.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "id desc"
        );

        if (cursor.moveToFirst()) {
            do {
                var date = cursor.getString(cursor.getColumnIndexOrThrow(BookingDAO.Columns.DATE));
                var user = cursor.getString(cursor.getColumnIndexOrThrow(BookingDAO.Columns.USER));
                var services = cursor.getString(cursor.getColumnIndexOrThrow(BookingDAO.Columns.SERVICES));
                var totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(BookingDAO.Columns.TOTAL_PRICE));

                bookings.add(new Booking(date, user, services.split(","), totalPrice));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookings;
    }

    public void upsertBooking(Booking newBooking){
        var db = dbHelper.getWritableDatabase();

        var values = new ContentValues();
        values.put(BookingDAO.Columns.DATE, newBooking.getDate());
        values.put(BookingDAO.Columns.USER, newBooking.getUser());
        values.put(BookingDAO.Columns.SERVICES, String.join(",", newBooking.getServices()));
        values.put(BookingDAO.Columns.TOTAL_PRICE, newBooking.getTotalPrice());
        db.insert(BookingDAO.TABLE_NAME, null, values);
    }
}
