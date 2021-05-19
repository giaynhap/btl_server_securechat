package com.giaynhap.securechat.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.utils.CascadeSave;
import com.giaynhap.securechat.utils.serializable.UUIDDeserializer;
import com.giaynhap.securechat.utils.serializable.UUIDSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Document("user_info")
public class UserInfo {
    @Id
    public ObjectId _id;

    @Field(name="name")
    private String name;

    @Field(name = "address")
    private  String address ;

    @DBRef(lazy = true)
    @Field("user_key")
    private UserKey userKey;


    @Field(name = "dob")
    private LocalDateTime dob ;

    @Field(name = "phone")
    private String phone ;

    @JsonSerialize(using = UUIDSerializer.class)
    @JsonDeserialize(using = UUIDDeserializer.class)
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId UUID) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null)
            return;
        if (phone.startsWith("0")){
            phone = "84"+ phone.substring(1,phone.length());
        }
        this.phone = phone;
    }

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public UserInfo(ObjectId _id) {
        this._id = _id;
    }

    public UserInfo(){

    }
}
