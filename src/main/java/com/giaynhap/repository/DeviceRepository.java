package com.giaynhap.repository;

import com.giaynhap.model.Contact;
import com.giaynhap.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository  extends CrudRepository<Device, String> {
    @Query(nativeQuery=true,
            value="select * from device where user_uuid = :user_uuid and device_code = :device_code" )
     Device getDeviceByDeviceCode(@Param("user_uuid") String userUuid,@Param("device_code") String deviceCode );
    @Query(nativeQuery=true,
            value="select * from device where user_uuid = :user_uuid" )
    List<Device> getUserDevices(@Param("user_uuid") String userUuid);

}
