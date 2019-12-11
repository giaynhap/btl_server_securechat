package com.giaynhap.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_uuid")
    private String userUuid;

    @Column(name = "contact_uuid")
    private  String contactUuid;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "level")
    private  int level;

    @Column(name = "custom_name")
    private  String customName;

    @ManyToOne(optional=false)
    @JoinColumn(name = "contact_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private Users contact;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_uuid",referencedColumnName = "uuid", insertable=false, updatable=false)
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getContactUuid() {
        return contactUuid;
    }

    public void setContactUuid(String contactUuid) {
        this.contactUuid = contactUuid;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int name) {
        this.level = level;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getContactName() {
        if (customName != null && !customName.isEmpty()){
            return customName;
        }
        if (contact == null){
            return "Undefined";
        }
        return contact.getName();
    }

    public Users getContact() {
        return contact;
    }

    public void setContact(Users contact) {
        this.contact = contact;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
