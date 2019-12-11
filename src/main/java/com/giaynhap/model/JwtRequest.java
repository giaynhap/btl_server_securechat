package com.giaynhap.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String username;
    private String password;
    private String token;
    private  Device device;
    //need default constructor for JSON Parsing
    public JwtRequest()
    {

    }
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}