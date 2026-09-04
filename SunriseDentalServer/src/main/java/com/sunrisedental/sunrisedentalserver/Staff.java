/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisedental.sunrisedentalserver;

/**
 *
 * @author Sadhu
 */
public class Staff {
    private int staffId;
    private String username;
    private String passwordHash;

    public Staff() {
    }

    public Staff(int staffId, String username, String passwordHash) {
        this.staffId = staffId;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
