package com.saloonbooking.persistence.models;

public class BookingDAO {
    public static final String TABLE_NAME = "bookings";

    public static class Columns {
        public static final String ID = "id";
        public static final String DATE = "date";
        public static final String USER = "user";
        public static final String SERVICES = "services"; // Store as comma-separated values
        public static final String TOTAL_PRICE = "total_price";
    }
}
