package com.saloonbooking.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.saloonbooking.persistence.repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final BookingRepository repository;
    private final MutableLiveData<List<Booking>> bookingHistory;

    public HomeViewModel(BookingRepository repository) {
        this.repository = repository;

        bookingHistory = new MutableLiveData<>();
        bookingHistory.setValue(this.repository.getAllBookings());
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
             current.add(0, newBooking);
         }

        bookingHistory.setValue(current);
        repository.upsertBooking(newBooking);
        return bookingHistory;
    }
}