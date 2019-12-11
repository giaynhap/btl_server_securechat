package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserKeyDTO implements Serializable {
    @JsonProperty("uuid")
    private String UUID;
    @JsonProperty("publickey")
    private String publicKey;
    @JsonProperty("privatekey")
    private  String privateKey ;

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
}
