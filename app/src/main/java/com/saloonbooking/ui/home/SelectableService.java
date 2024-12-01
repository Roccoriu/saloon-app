package com.saloonbooking.ui.home;

public  class SelectableService {
    private final String name;
    private final int price;

    public SelectableService(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }
}
