package com.giaynhap.securechat.repository;

import com.giaynhap.securechat.model.Device;
import com.giaynhap.securechat.model.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DeviceRepository extends MongoRepository<Device, ObjectId> {
    @Query("{'device_code': 0? }")
    Device getByDeviceCode(String code);

}
