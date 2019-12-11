package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.config.LocalDateTimeDeserializer;
import com.giaynhap.config.LocalDateTimeSerializer;
import com.giaynhap.model.Contact;
import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoDTO implements Serializable {
    @JsonProperty("uuid")
    private String UUID;
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



    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
        UserInfoDTO dto = modelMapper.map(user, UserInfoDTO.class);
        if (user.getUserKey() != null) {
            dto.publicKey = user.getUserKey().getPublicKey();

        }
        return dto;
    }
    public UserInfo toEntity(ModelMapper modelMapper){
        return  modelMapper.map(this, UserInfo.class);
    }
}
