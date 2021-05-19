package com.giaynhap.securechat.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document("message")
public class Message {
    @Id
    @Field(name = "_id")
    private ObjectId uuid;

    @Field(name = "type")
    private int type;

    @Field(name = "device_code")
    private String deviceCode;

    @Field(name = "time")
    private LocalDateTime time;

    @Field(name="encrypt")
    private Boolean encrypt;

    @Field(name="payload")
    private String payload;

    @DBRef(lazy = true)
    private UserInfo sender;

    @DBRef(lazy = true)
    private Conversation conversation;

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }


    public Conversation getConversation() {
        return conversation;
    }

    public String getSenderUuid() {
        if (this.sender == null || this.sender.getId() == null){
            return null;
        }
        return this.sender.getId().toHexString();
    }

    public ObjectId getUuid() {
        return uuid;
    }

    public void setUuid(ObjectId uuid) {
        this.uuid = uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getUserUuid() {
        if (this.sender == null || this.sender.getId() == null){
            return null;
        }
        return this.sender.getId().toHexString();
    }


    public String getThreadUuid() {
        return this.getConversation().getUUID().toHexString();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }
}
