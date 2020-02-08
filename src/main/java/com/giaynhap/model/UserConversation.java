package com.giaynhap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.config.LocalDateTimeDeserializer;
import com.giaynhap.config.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="user_conversation")
public class UserConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "thread_uuid")
    private String threadUuid;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "enkey")
    private String key;

    @JsonProperty( "last_seen")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    public String getThreadUuid() {
        return threadUuid;
    }

    public void setThreadUuid(String threadUuid) {
        this.threadUuid = threadUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

}
