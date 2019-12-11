package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.config.LocalDateTimeDeserializer;
import com.giaynhap.config.LocalDateTimeSerializer;
import com.giaynhap.model.Message;
import com.giaynhap.model.UserInfo;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Id;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

    @JsonProperty("conversation")
    private  ConversationDTO conversation;

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
    }

    public ConversationDTO getConversation() {
        return conversation;
    }

    public void setConversation(ConversationDTO conversation) {
        this.conversation = conversation;
    }
    public static MessageDTO fromEntity(ModelMapper modelMapper, Message message){
        MessageDTO dto = modelMapper.map(message, MessageDTO.class);
        if (message.getSenderInfo() != null)
        dto.sender = UserInfoDTO.fromEntity(modelMapper,message.getSenderInfo());
        return dto;
    }
    public Message toEntity(ModelMapper modelMapper){
        return  modelMapper.map(this, Message.class);
    }

}
