package com.giaynhap.securechat.service;

import com.giaynhap.securechat.model.Device;
import com.giaynhap.securechat.repository.DeviceRepository;
import com.giaynhap.securechat.service.serviceInterface.DeviceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Device getDeviceByDeviceCode(String code) {

        return deviceRepository.getByDeviceCode(code);
    }

    @Override
    public Device getDeviceByDeviceCode(String user, String code) {
        Query q = Query.query(Criteria.where("user_uuid").is(new ObjectId(user)));
        try {
            return mongoTemplate.find(q,Device.class).get(0);
        }catch (Exception e) {
            return null;
        }

    }
}
