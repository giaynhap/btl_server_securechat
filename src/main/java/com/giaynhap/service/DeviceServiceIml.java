package com.giaynhap.service;

import com.giaynhap.model.Device;
import com.giaynhap.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceServiceIml implements DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Override
    public Device getDeviceByDeviceCode(String uuid, String code) {
        return deviceRepository.getDeviceByDeviceCode(uuid,code);
    }

    @Override
    public List<Device> getUserDevices(String uuid) {
        return deviceRepository.getUserDevices(uuid);
    }

    @Override
    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void removeDevice(Device device) {
        deviceRepository.delete(device);
    }


}
