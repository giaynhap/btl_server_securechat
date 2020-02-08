package com.giaynhap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.config.LocalDateTimeDeserializer;
import com.giaynhap.config.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRegistRequest implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private  String address ;
    @JsonProperty("publickey")
    private  String publicKey ;

    @JsonProperty( "dob")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dob ;
    @JsonProperty( "username")
    private String username;
    @JsonProperty( "password")
    private String password;

    @JsonProperty( "phonenumber")
    private String phonenumber;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
