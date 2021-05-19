package com.giaynhap.securechat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.utils.serializable.UUIDDeserializer;
import com.giaynhap.securechat.utils.serializable.UUIDSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("device")
public class Device {
    @Id
    @JsonProperty( "uuid" )
    private ObjectId _id;

    @JsonProperty( "user_uuid" )
    @Field("user_uuid")
    private ObjectId userUuid;

    @JsonProperty( "device_code" )
    @Field("device_code")
    private String deviceCode;

    @JsonProperty( "device_os" )
    @Field("device_os")
    private String deviceOs;

    @JsonSerialize(using = UUIDSerializer.class)
    @JsonDeserialize(using = UUIDDeserializer.class)
    public ObjectId getUUID() {
        return _id;
    }

    public void setUUID(ObjectId UUID) {
        this._id = UUID;
    }

    public ObjectId getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(ObjectId userUuid) {
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
