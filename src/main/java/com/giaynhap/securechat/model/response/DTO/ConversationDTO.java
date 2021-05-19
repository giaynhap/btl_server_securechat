package com.giaynhap.securechat.model.response.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.Conversation;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.utils.serializable.DateTimeDeserializer;
import com.giaynhap.securechat.utils.serializable.DateTimeSerializer;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationDTO implements Serializable {

    @JsonProperty( "uuid" )
    private String UUID;

    @JsonProperty( "user_uuid" )
    private  String userUuid;

    @JsonProperty( "thread_name" )
    private  String name;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty( "create_at" )
    private LocalDateTime createAt;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty ( "last_msg_at")
    private LocalDateTime lastMessageAt;

    @JsonProperty( "users" )
    private List<UserInfoDTO> users;

    @JsonProperty( "user_conversations" )
    private List<UserConversationDTO> userConversations;

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

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
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


    public static String createName(List<UserInfoDTO> users, String uuid){
        String threadName = "";
        for (UserInfoDTO u : users) {
            if (!u.getId().equals(uuid)) {
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
        if (conversation == null){
            return null;
        }
        try {
            dto = modelMapper.map(conversation, ConversationDTO.class);
        }catch ( Exception e){
            e.printStackTrace();
        }

        if (conversation.getUser() != null){
            dto.setUserUuid(conversation.getUser().getId().toHexString());
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

    public List<UserConversationDTO> getUserConversations() {
        return userConversations;
    }

    public void setUserConversations(List<UserConversationDTO> userConversations) {
        this.userConversations = userConversations;
    }
}
