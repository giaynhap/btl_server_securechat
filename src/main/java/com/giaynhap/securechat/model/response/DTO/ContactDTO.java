package com.giaynhap.securechat.model.response.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.utils.serializable.DateTimeDeserializer;
import com.giaynhap.securechat.utils.serializable.DateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

public class ContactDTO implements Serializable {
    @JsonProperty("id")
    private String id;

    @JsonProperty("contact_uuid")
    private  String contactUuid;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("level")
    private  int level;

    @JsonProperty("contact_name")
    private  String contactName;

    @JsonProperty("online")
    private  boolean online;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String customName) {
        this.contactName = customName;
    }

    public static ContactDTO fromEntity(ModelMapper modelMapper, Contact contact){
        if (contact == null){
            return null;
        }
        ContactDTO dto = modelMapper.map(contact, ContactDTO.class);
      //  dto.online = UserOnlineController.getInstance().isOnline(dto.contactUuid);
        return dto;
    }
    public Contact toEntity(ModelMapper modelMapper){
        Contact contact =  modelMapper.map(this, Contact.class);
        contact.setCustomName(this.getContactName());
        return contact;
    }

    public boolean isOnline() {
        return online;
    }
}
