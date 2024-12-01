package com.saloonbooking.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.saloonbooking.persistence.repository.BookingRepository;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final BookingRepository repository;

    public HomeViewModelFactory(BookingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
