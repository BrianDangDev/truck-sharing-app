package com.data.trucksharingapp.model;

public class TruckModel {
    public int truck_img;
    public String truck_name;
    public String truck_type;

    public TruckModel(String truck_name, String truck_type, int truck_img) {
        this.truck_name = truck_name;
        this.truck_type = truck_type;
        this.truck_img = truck_img;
    }
}
