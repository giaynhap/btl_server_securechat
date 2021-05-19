package com.giaynhap.securechat.model.request;

import com.giaynhap.securechat.model.Device;

import java.io.Serializable;

public class AuthenRequest implements Serializable {

    private String username;
    private String password;
    private String token;
    private Device device;
    private String transactionId;

    public AuthenRequest()
    {

    }
    public AuthenRequest(String username, String password) {
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

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
