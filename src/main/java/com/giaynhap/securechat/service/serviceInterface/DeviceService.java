package com.giaynhap.securechat.service.serviceInterface;

import com.giaynhap.securechat.model.Device;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {
    Device getDeviceByDeviceCode(String code);
    Device getDeviceByDeviceCode(String user,String code);
    Device saveDevice(Device device);
}
