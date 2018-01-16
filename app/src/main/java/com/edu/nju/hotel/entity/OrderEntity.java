package com.edu.nju.hotel.entity;

import java.util.List;

/**
 * Created by zcy on 2017/7/6.
 *
 */

public class OrderEntity {

    /**
     * userId : 1
     * address : 南京市南大
     * hotelType : Business
     * roomType : Business
     * roomNum : 1
     * minPrice : 100
     * maxPrice : 900
     * facilities : ["wifi"]
     * orderTime : 2016-06-10
     * startTime : 2016-06-11
     * endTime : 2016-06-12
     */

    private String userId;
    private String address;
    private String hotelType;
    private String roomType;
    private String roomNum;
    private String minPrice;
    private String maxPrice;
    private String orderTime;
    private String startTime;
    private String endTime;
    private List<String> facilities;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
}
