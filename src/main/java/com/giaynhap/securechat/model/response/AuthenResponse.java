package com.giaynhap.securechat.model.response;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;

import java.io.Serializable;

public class AuthenResponse implements Serializable {

    private  String jwttoken;
    private  String refreshToken;
    private UserInfoDTO user;

    public UserInfoDTO getUser() {
        return user;
    }

    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public AuthenResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public AuthenResponse(String jwttoken,String refreshToken) {
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