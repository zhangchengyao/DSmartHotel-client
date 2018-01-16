package com.edu.nju.hotel.entity;

/**
 * Created by zcy on 2017/7/6.
 *
 */

public class TenantEntity {

    /**
     * economic : normal
     * education : small
     * gender : m
     * id : 1
     * name : hqq
     * password : 123
     * phonenum : 15850590186
     * preference : economical
     * vocation : student
     */

    private String economic;
    private String education;
    private String gender;
    private int id;
    private String name;
    private String password;
    private String phonenum;
    private String preference;
    private String vocation;

    public String getEconomic() {
        return economic;
    }

    public void setEconomic(String economic) {
        this.economic = economic;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }
}
