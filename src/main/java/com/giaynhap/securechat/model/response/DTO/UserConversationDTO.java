package com.giaynhap.securechat.model.response.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.UserConversation;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.utils.serializable.DateTimeDeserializer;
import com.giaynhap.securechat.utils.serializable.DateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserConversationDTO  implements Serializable {
    @JsonProperty("id")
    private String id;

    @JsonProperty("key")
    private String key;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonProperty("last_seen")
    private LocalDateTime lastSeen;

    @JsonProperty("user_uuid")
    private String userUuid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public static UserConversationDTO fromEntity(ModelMapper modelMapper, UserConversation user){
        if (user == null){
            return null;
        }
        UserConversationDTO dto = modelMapper.map(user, UserConversationDTO.class);

        return dto;
    }
    public UserConversation toEntity(ModelMapper modelMapper){
        return  modelMapper.map(this, UserConversation.class);
    }
}
