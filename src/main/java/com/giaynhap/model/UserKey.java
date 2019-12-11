package com.giaynhap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user_key")
public class UserKey {
    @Id
    @Column(name = "uuid")
    private String UUID;

    @Column(name="publickey")
    private String publicKey;

    @Column(name = "privatekey")
    private  String privateKey ;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPublicKey() {
        if (publicKey == null){
            return "";
        }
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        if (privateKey == null){
            return "";
        }
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {

        this.privateKey = privateKey;
    }
}
