package com.giaynhap.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.config.LocalDateTimeDeserializer;
import com.giaynhap.config.LocalDateTimeSerializer;
import com.giaynhap.controller.UserOnlineController;
import com.giaynhap.model.Contact;
import com.giaynhap.model.Conversation;
import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import org.modelmapper.ModelMapper;
import com.giaynhap.model.UserConversation;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationDTO implements Serializable {

    @JsonProperty( "uuid" )
    private String UUID;

    @JsonProperty( "user_uuid" )
    private  String user_uuid;

    @JsonProperty( "thread_name" )
    private  String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty( "create_at" )
    private LocalDateTime createAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty ( "last_msg_at")
    private LocalDateTime lastMessageAt;

    @JsonProperty( "users" )
    private List<UserInfoDTO> users;

    @JsonProperty( "userConversations" )
    private List<UserConversation> userConversations;

    @JsonProperty( "un_read" )
    private  Integer unread;

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public static String createName(List<UserInfo> users, String uuid){
        String threadName = "";
        for (UserInfo u : users) {
            if (!u.getUUID().equals(uuid)) {
                threadName += u.getName() + " ";
            }
        }

        if (threadName.isEmpty()){
            threadName = users.get(0).getName();
        }
        return threadName;
    }
    public static ConversationDTO fromEntity(ModelMapper modelMapper, Conversation conversation){
        ConversationDTO dto = null;

        try {
            dto = modelMapper.map(conversation, ConversationDTO.class);
        }catch ( Exception e){
            e.printStackTrace();
        }

        if (conversation.getUsers() != null) {
            dto.users = new ArrayList<UserInfoDTO>();
            for (UserInfo u : conversation.getUsers()) {
                dto.users.add(UserInfoDTO.fromEntity(modelMapper, u));

            }
        }

        return dto;
    }
    public Conversation toEntity(ModelMapper modelMapper){
        Conversation conversation =  modelMapper.map(this, Conversation.class);
        return conversation;
    }

    public List<UserInfoDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfoDTO> users) {
        this.users = users;
    }

    public List<UserConversation> getUserConversations() {
        return userConversations;
    }

    public void setUserConversations(List<UserConversation> userConversations) {
        this.userConversations = userConversations;
    }
}
