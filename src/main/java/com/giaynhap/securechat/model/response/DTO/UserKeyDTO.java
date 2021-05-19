package com.giaynhap.securechat.model.response.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.giaynhap.securechat.model.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

public class UserKeyDTO implements Serializable {
    @JsonProperty("uuid")
    private String UUID;
    @JsonProperty("publickey")
    private String publicKey;
    @JsonProperty("privatekey")
    private  String privateKey ;
    @DBRef(lazy = true)
    @Field("user_key")
    private UserInfo userInfo;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public static UserInfoDTO fromEntity(ModelMapper modelMapper, UserInfo user){
        if (user == null){
            return null;
        }
        UserInfoDTO dto = modelMapper.map(user, UserInfoDTO.class);
        dto.setId( user.getId().toHexString());


        return dto;
    }

    public UserInfo toEntity(ModelMapper modelMapper){
        return  modelMapper.map(this, UserInfo.class);
    }

}
