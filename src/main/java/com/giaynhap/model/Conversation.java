package com.giaynhap.model;



import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="conversation")
public class Conversation {

    @Id
    @Column(name = "uuid")
    private String UUID;

    @Column(name = "user_uuid")
    private  String user_uuid;

    @Column(name = "thread_name")
    private  String name;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column (name = "last_msg_at")
    private LocalDateTime lastMessageAt;


    @ManyToMany(cascade = {
            CascadeType.MERGE
    } )
    @JoinTable(name = "user_conversation",
            joinColumns = @JoinColumn(name = "thread_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid")
    )
    private List<UserInfo> users;


    private Integer unread;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "thread_uuid",referencedColumnName = "uuid")
    private List<UserConversation> userConversations;


    public List<UserInfo> getUsers() {
        if (users == null){
            return new ArrayList<UserInfo>();
        }
        return users;
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

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
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

    public List<UserConversation> getUserConversations() {
        return userConversations;
    }

    public void setUserConversations(List<UserConversation> userConversations) {
        this.userConversations = userConversations;
    }
}
