package com.giaynhap.securechat.model.response.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.model.Device;
import com.giaynhap.securechat.utils.serializable.UUIDDeserializer;
import com.giaynhap.securechat.utils.serializable.UUIDSerializer;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

public class DeviceDTO {

    @JsonProperty( "uuid" )
    private String _id;

    @JsonProperty( "user_uuid" )
    private String userUuid;

    @JsonProperty( "device_code" )
    @Field("device_code")
    private String deviceCode;

    @JsonProperty( "device_os" )
    @Field("device_os")
    private String deviceOs;

    @JsonSerialize(using = UUIDSerializer.class)
    @JsonDeserialize(using = UUIDDeserializer.class)
    public String getUUID() {
        return _id;
    }

    public void setUUID(String UUID) {
        this._id = UUID;
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

    public static DeviceDTO fromEntity(ModelMapper modelMapper, Device device){
        if (device == null){
            return null;
        }
        DeviceDTO dto = modelMapper.map(device, DeviceDTO.class);
        //  dto.online = UserOnlineController.getInstance().isOnline(dto.contactUuid);
        return dto;
    }
    public Device toEntity(ModelMapper modelMapper){
        Device contact =  modelMapper.map(this, Device.class);

        return contact;
    }
}
