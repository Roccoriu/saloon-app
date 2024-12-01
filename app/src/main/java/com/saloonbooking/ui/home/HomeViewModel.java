package com.saloonbooking.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Booking>> bookingHistory;

    public HomeViewModel() {
        bookingHistory = new MutableLiveData<>();
    }

    public LiveData<List<Booking>> getBookingHistory() {
        return bookingHistory;
    }

    public LiveData<List<Booking>> addBooking(Booking newBooking){
        var current = bookingHistory.getValue();

         if (current == null){
             current = new ArrayList<>();
             current.add(newBooking);
         } else{
             current.add(newBooking);
         }

        bookingHistory.setValue(current);
        return bookingHistory;
    }
}