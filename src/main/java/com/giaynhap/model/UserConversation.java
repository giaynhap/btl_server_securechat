package com.giaynhap.model;

import javax.persistence.*;

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

    @ManyToOne(optional=false)
    @JoinColumn(name = "thread_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private Conversation conversation;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private UserInfo user;

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

    public Conversation getConversation() {
        return conversation;
    }

    public UserInfo getUser() {
        return user;
    }


}
