package com.giaynhap.securechat.model.response.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.Conversation;
import com.giaynhap.securechat.model.Message;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.utils.serializable.DateTimeDeserializer;
import com.giaynhap.securechat.utils.serializable.DateTimeSerializer;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageDTO implements Serializable {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("type")
    private int type;

    @JsonProperty("device_code")
    private String deviceCode;

    @JsonProperty("user_uuid")
    private String userUuid;

    @JsonProperty("thread_uuid")
    private String threadUuid;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty("time")
    private LocalDateTime time;

    @JsonProperty("encrypt")
    private Boolean encrypt;

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("sender_uuid")
    private String senderUuid;

    @JsonProperty("sender")
    private UserInfoDTO sender;

    @JsonProperty("thread_name")
    private String threadName;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
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

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
        this.sender = sender;
        if (sender != null ) {
            this.senderUuid = sender.getId();
        }
    }

    public static MessageDTO fromEntity(ModelMapper modelMapper, Message message){
        MessageDTO dto = modelMapper.map(message, MessageDTO.class);
        if (message.getSender() != null) {
            dto.sender = UserInfoDTO.fromEntity(modelMapper, message.getSender());
            dto.senderUuid = dto.sender.getId();
        }

        return dto;
    }
    public Message toEntity(ModelMapper modelMapper){
        Message msg = modelMapper.map(this, Message.class);
        if ( msg.getSender() == null && this.getSender() != null){
            UserInfo sender = this.getSender().toEntity(modelMapper);
            msg.setSender(sender);
        }

        if ( this.getUuid() != null && msg.getUuid() == null){
            msg.setUuid(new ObjectId(this.getUuid()));
        }

        if ( msg.getSender().getId() == null){
            msg.getSender().setId(new ObjectId(this.getSenderUuid()));
        }

        if ( msg.getConversation() == null && this.getThreadUuid() != null){
            Conversation conversation = new Conversation();
            conversation.setUUID( new ObjectId(this.getThreadUuid()));
            msg.setConversation(conversation);
        }

        return msg;

    }

}
