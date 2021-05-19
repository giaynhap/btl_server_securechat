package com.giaynhap.securechat.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("user_key")
public class UserKey {
    @Id
    private ObjectId _id;

    @Field(name="publickey")
    private String publicKey;

    @Field(name = "privatekey")
    private  String privateKey ;

    public ObjectId getUUID() {
        return _id;
    }

    public void setUUID(ObjectId UUID) {
        this._id = UUID;
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
