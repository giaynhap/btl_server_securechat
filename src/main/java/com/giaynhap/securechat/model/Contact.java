package com.giaynhap.securechat.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@CompoundIndexes({
        @CompoundIndex(name = "contact_uuid", def = "{ 'contact': 1, 'user': 1 }", unique = true)
})
@Document("contact")
public class Contact {
    @Id
    private ObjectId _id;

    @Field(name = "create_at")
    private LocalDateTime createAt;

    @Field(name = "level")
    private  int level;

    @Field(name = "custom_name")
    private  String customName;

    @DBRef(lazy = true)
    private UserInfo contact;

    @DBRef(lazy = true)
    private UserInfo user;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getUserUuid() {
        return user.getId().toHexString();
    }


    public String getContactUuid() {
        return contact.getId().toHexString();
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

    public UserInfo getContact() {
        return contact;
    }

    public void setContact(UserInfo contact) {
        this.contact = contact;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

}
