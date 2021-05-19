package com.giaynhap.securechat.model;

import com.giaynhap.securechat.utils.CascadeSave;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDateTime;

@Document("user")
public class User {

    @Id
    private ObjectId _id;

    @DBRef(lazy = true)
    @Field("user_info")
    private UserInfo userInfo;

    @Indexed(unique = true)
    @Field(name="account")
    private String account;

    @Field(name = "password")
    private  String password;

    @Field(name = "create_at")
    private LocalDateTime create_at;

    @Field(name = "name")
    private  String name;

    @Field(name = "token")
    private  String token;

    @Field(name = "token_time")
    private  LocalDateTime tokenTime;

    @Field(name = "enable")
    private  boolean enable;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LocalDateTime getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(LocalDateTime tokenTime) {
        this.tokenTime = tokenTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id){
        this._id = id;
    }
}
