package com.giaynhap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="device")
public class Device {
    @Id
    @JsonProperty( "uuid" )
    @Column(name = "uuid")
    private String UUID;
    @JsonProperty( "user_uuid" )
    @Column(name = "user_uuid")
    private String userUuid;
    @JsonProperty( "device_code" )
    @Column(name = "device_code")
    private String deviceCode;
    @JsonProperty( "device_os" )
    @Column(name = "device_os")
    private String deviceOs;

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

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }
}
