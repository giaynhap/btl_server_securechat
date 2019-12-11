package com.giaynhap.model;



import javax.persistence.*;
import java.sql.Date;

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
    private Date create_at;

    @Column(name = "name")
    private  String name;

    @Column(name = "token")
    private  String token;

    @ManyToOne(optional=false)
    @JoinColumn(name = "uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private UserInfo userInfo;

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

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
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
}
