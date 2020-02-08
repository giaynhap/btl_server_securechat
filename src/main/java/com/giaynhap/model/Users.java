package com.giaynhap.model;


import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
public class Users {

    @Id
    @Column(name = "uuid")
    private String UUID;

    @Column(name="account")
    private String account;

    @Column(name = "password")
    private  String password;

    @Column(name = "create_at")
    private LocalDateTime create_at;

    @Column(name = "name")
    private  String name;

    @Column(name = "token")
    private  String token;

    @Column(name = "token_time")
    private  LocalDateTime tokenTime;

    @ManyToOne(optional=false)
    @JoinColumn(name = "uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private UserInfo userInfo;

    @Column(name = "enable")
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

    public String getName() {
        if (userInfo != null)
                return userInfo.getName();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
}
