package com.giaynhap.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="messages")
public class Message {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "type")
    private int type;

    @Column(name = "device_code")
    private String deviceCode;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "thread_uuid")
    private String threadUuid;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name="encrypt")
    private Boolean encrypt;

    @Column(name="payload")
    private String payload;

    @Column(name = "sender_uuid")
    private String senderUuid;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private UserInfo userInfo;

    @ManyToOne(optional=false)
    @JoinColumn(name = "sender_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private UserInfo senderInfo;

    @ManyToOne(optional=false)
    @JoinColumn(name = "thread_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private Conversation conversation;

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public UserInfo getSenderInfo() {
        return senderInfo;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getThreadUuid() {
        return threadUuid;
    }

    public void setThreadUuid(String threadUuid) {
        this.threadUuid = threadUuid;
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
}
