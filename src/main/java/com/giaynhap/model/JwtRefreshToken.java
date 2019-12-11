package com.giaynhap.model;

import java.io.Serializable;

public class JwtRefreshToken implements Serializable
{

    private String refreshToken;
    private String token;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtRefreshToken(String refreshToken, String token) {
        this.refreshToken = refreshToken;
        this.token = token;
    }
}
