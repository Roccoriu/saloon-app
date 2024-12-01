package com.saloonbooking.ui.priceList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PriceListViewModel extends ViewModel {
    private final MutableLiveData<List<Service>> services = new MutableLiveData<>();

    public PriceListViewModel() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("Haircut", "₵30"));
        serviceList.add(new Service("Shampoo & Styling", "₵50"));
        serviceList.add(new Service("Manicure", "₵25"));
        serviceList.add(new Service("Pedicure", "₵30"));
        serviceList.add(new Service("Facial Treatment", "₵60"));
        serviceList.add(new Service("Hair Coloring", "₵80"));
        serviceList.add(new Service("Braiding", "₵125"));
        serviceList.add(new Service("Makeup", "₵120"));
        serviceList.add(new Service("Beard Grooming", "₵20"));
        services.setValue(serviceList);
    }

    public LiveData<List<Service>> getServices() {
        return services;
    }

    public static class Service {
        public final String name;
        public final String price;

        public Service(String name, String price) {
            this.name = name;
            this.price = price;
        }
    }
}