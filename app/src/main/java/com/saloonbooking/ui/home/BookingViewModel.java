package com.saloonbooking.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class BookingViewModel extends ViewModel {
    // Represents the list of services
    private final MutableLiveData<List<SelectableService>> services = new MutableLiveData<>();

    // Represents the selected services
    private final MutableLiveData<List<SelectableService>> selectedServices = new MutableLiveData<>(new ArrayList<>());

    // Represents the user's name
    private final MutableLiveData<String> userName = new MutableLiveData<>("");

    // Represents the selected date
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>("");

    // Represents the total price
    private final MutableLiveData<Integer> totalPrice = new MutableLiveData<>(0);

    public BookingViewModel() {
        // Initialize the list of services
        List<SelectableService> initialServices = new ArrayList<>();
        initialServices.add(new SelectableService("Haircut", 30));
        initialServices.add(new SelectableService("Shampoo & Styling", 50));
        initialServices.add(new SelectableService("Manicure", 25));
        initialServices.add(new SelectableService("Pedicure", 30));
        initialServices.add(new SelectableService("Facial Treatment", 60));
        initialServices.add(new SelectableService("Hair Coloring", 80));
        services.setValue(initialServices);
    }

    // Getters
    public LiveData<List<SelectableService>> getServices() {
        return services;
    }

    public LiveData<List<SelectableService>> getSelectedServices() {
        return selectedServices;
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public LiveData<Integer> getTotalPrice() {
        return totalPrice;
    }

    // Actions
    public void setUserName(String name) {
        userName.setValue(name);
    }

    public void setSelectedDate(String date) {
        selectedDate.setValue(date);
    }

    public void toggleServiceSelection(SelectableService service) {
        List<SelectableService> currentSelection = selectedServices.getValue();
        if (currentSelection == null) {
            currentSelection = new ArrayList<>();
        }
        if (currentSelection.contains(service)) {
            currentSelection.remove(service);
        } else {
            currentSelection.add(service);
        }
        selectedServices.setValue(currentSelection);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int total = 0;
        List<SelectableService> currentSelection = selectedServices.getValue();

        if (currentSelection != null) {
            for (SelectableService service : currentSelection) {
                total += service.getPrice();
            }
        }
        totalPrice.setValue(total);
    }
}