package com.giaynhap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.giaynhap.config.JodaDateTimeConverter;
import com.giaynhap.config.LocalDateTimeSerializer;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity(name = "user_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    @Id
    @Column(name = "uuid")
    private String UUID;

    @Column(name="name")
    private String name;

    @Column(name = "address")
    private  String address ;


    @Column(name = "dob")
    private LocalDateTime dob ;

    @Column(name = "phone")
    private String phone ;


    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(
            action = NotFoundAction.IGNORE)
    @JoinColumn(name = "uuid",referencedColumnName = "uuid", insertable=false, updatable=false,nullable = true)
    private UserKey userKey;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserKey getUserKey() {
        return userKey;
    }
}
