package com.giaynhap.securechat.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document("conversation")
public class Conversation {
    @Id
    @Field(name = "uuid")
    private ObjectId _id;

    @DBRef(lazy = true)
    private  UserInfo user;

    @Field(name = "thread_name")
    private  String name;

    @Field(name = "create_at")
    private LocalDateTime createAt;

    @Field(name = "last_msg_at")
    private LocalDateTime lastMessageAt;

    @Field(name = "unread")
    private Integer unread;


    @Field(name = "is_group")
    private Boolean isGroup;

    @DBRef(lazy = true)
    @Field(name = "user_conversations")
    private List<UserConversation> userConversations;

    public List<UserInfo> getUsers() {
        if (userConversations == null){
            return new ArrayList<UserInfo>();
        }
        return userConversations.stream().map(m -> m.getUser()).collect(Collectors.toList());
    }

    public Integer getUnread() {
        if (unread == null){
            return 0;
        }
        return unread;
    }

    public void setUnread(Integer unread) {

        this.unread = unread;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public ObjectId getUUID() {
        return _id;
    }

    public void setUUID(ObjectId UUID) {
        this._id = UUID;
    }

    public String getUser_uuid() {
        return user.getId().toHexString();
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<UserConversation> getUserConversations() {
        return userConversations;
    }

    public void setUserConversations(List<UserConversation> userConversations) {
        this.userConversations = userConversations;
        if (isGroup == null) {
            isGroup = this.userConversations.size() > 2;
        }
    }

    public List<ObjectId> getUserIds() {
        if (this.userConversations == null){
            return null;
        }
        return this.userConversations.stream().map(m -> m.getUser().getId()).collect(Collectors.toList());
    }

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }
}
