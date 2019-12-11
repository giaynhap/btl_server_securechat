package com.giaynhap.model;

import com.giaynhap.config.AppConstant;

import java.io.Serializable;
public class JwtResponse implements Serializable {

    private  String jwttoken;
    private  String refreshToken;


    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
    public JwtResponse(String jwttoken,String refreshToken) {
        this.jwttoken = jwttoken;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
            return this.jwttoken;
        }
    public String getRefreshToken() {
        return this.refreshToken;
    }
    public long getDuringTime(){
        return AppConstant.JWT_TOKEN_VALIDITY;
    }

}