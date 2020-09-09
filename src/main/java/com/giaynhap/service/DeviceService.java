package com.giaynhap.service;

import com.giaynhap.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {
    public Device getDeviceByDeviceCode(String uuid, String code);
    public List<Device> getUserDevices(String uuid);
    public Device addDevice(Device device);
    public void removeDevice(Device device);

}
