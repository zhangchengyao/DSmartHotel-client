package com.edu.nju.hotel.service;

/**
 * Created by luoxuechun on 2017/5/19.
 */
public interface LoginService {
    boolean isTenant(String name,String password);
    boolean isLandlord(String name,String password);
}
