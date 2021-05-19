package com.giaynhap.securechat.model.response.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.utils.serializable.DateTimeDeserializer;
import com.giaynhap.securechat.utils.serializable.DateTimeSerializer;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoDTO implements Serializable {
    @JsonProperty("uuid")
    private String _id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private  String address ;
    @JsonProperty("publickey")
    private  String publicKey ;

    @JsonProperty( "dob")
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime dob ;

    @JsonProperty("online")
    private  boolean online;

    @JsonProperty( "phone")
    private String phone ;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getId() {
        return _id;
    }

    public void setId(String UUID) {
        this._id = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public static UserInfoDTO fromEntity(ModelMapper modelMapper, UserInfo user){
        if (user == null){
            return null;
        }
        UserInfoDTO dto = modelMapper.map(user, UserInfoDTO.class);

        if (user.getUserKey() != null) {
            dto.publicKey = user.getUserKey().getPublicKey();
        }
        return dto;
    }
    public UserInfo toEntity(ModelMapper modelMapper){
        UserInfo info =  modelMapper.map(this, UserInfo.class);
        return info;
    }
}
