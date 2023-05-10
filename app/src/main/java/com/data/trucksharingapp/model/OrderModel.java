package com.data.trucksharingapp.model;

public class OrderModel {
    public int id;
    public String receiver_name;
    public String pickup_time;
    public String pickup_date;
    public String pickup_address;
    public String pick_lat;
    public String pick_long;
    public String dropoff_address;
    public String drop_lat;
    public String drop_long;
    public String goods_type;
    public String weight;
    public String length;
    public String width;
    public String height;
    public String quantity;
    public String vehicle_type;
    public Long time_millis;

    public OrderModel(int id, String receiver_name, String pickup_time, String pickup_date, String pickup_address, String pick_lat, String pick_long, String dropoff_address, String drop_lat, String drop_long, String goods_type, String weight, String length, String width, String height, String quantity, String vehicle_type, Long time_millis) {
        this.id = id;
        this.receiver_name = receiver_name;
        this.pickup_time = pickup_time;
        this.pickup_date = pickup_date;
        this.pickup_address = pickup_address;
        this.pick_lat = pick_lat;
        this.pick_long = pick_long;
        this.dropoff_address = dropoff_address;
        this.drop_lat = drop_lat;
        this.drop_long = drop_long;
        this.goods_type = goods_type;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        this.vehicle_type = vehicle_type;
        this.time_millis = time_millis;
    }

    public OrderModel(String receiver_name, String pickup_time, String pickup_date, String pickup_address, String pick_lat, String pick_long, String dropoff_address, String drop_lat, String drop_long, String goods_type, String weight, String length, String width, String height, String quantity, String vehicle_type, Long time_millis) {
        this.receiver_name = receiver_name;
        this.pickup_time = pickup_time;
        this.pickup_date = pickup_date;
        this.pickup_address = pickup_address;
        this.pick_lat = pick_lat;
        this.pick_long = pick_long;
        this.dropoff_address = dropoff_address;
        this.drop_lat = drop_lat;
        this.drop_long = drop_long;
        this.goods_type = goods_type;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        this.vehicle_type = vehicle_type;
        this.time_millis = time_millis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getPick_lat() {
        return pick_lat;
    }

    public void setPick_lat(String pick_lat) {
        this.pick_lat = pick_lat;
    }

    public String getPick_long() {
        return pick_long;
    }

    public void setPick_long(String pick_long) {
        this.pick_long = pick_long;
    }

    public String getDropoff_address() {
        return dropoff_address;
    }

    public void setDropoff_address(String dropoff_address) {
        this.dropoff_address = dropoff_address;
    }

    public String getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(String drop_lat) {
        this.drop_lat = drop_lat;
    }

    public String getDrop_long() {
        return drop_long;
    }

    public void setDrop_long(String drop_long) {
        this.drop_long = drop_long;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public Long getTime_millis() {
        return time_millis;
    }

    public void setTime_millis(Long time_millis) {
        this.time_millis = time_millis;
    }
}
