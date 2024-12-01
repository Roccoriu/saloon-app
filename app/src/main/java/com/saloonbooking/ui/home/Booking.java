package com.saloonbooking.ui.home;

import java.util.List;

public class Booking {
    private final String date;
    private final String user;
    private final String[] services;
    private final int totalPrice;

    public Booking(String date, String user, String[] services, int totalPrice){
        this.date= date;
        this.user = user;
        this.services= services;
        this.totalPrice= totalPrice;
    }

    public String getDate(){
        return date;
    }

    public String getUser(){
        return user;
    }

    public String[] getServices() {
        return services;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
