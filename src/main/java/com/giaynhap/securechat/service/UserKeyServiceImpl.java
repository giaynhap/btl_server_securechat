package com.giaynhap.securechat.service;

import com.giaynhap.securechat.model.UserKey;
import com.giaynhap.securechat.repository.UserKeyRepository;
import com.giaynhap.securechat.service.serviceInterface.UserKeyService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserKeyServiceImpl implements UserKeyService {
    @Autowired
    UserKeyRepository userKeyRepository;
    @Override
    public UserKey getKey(String uuid) {
        try {
            return userKeyRepository.findById(new ObjectId(uuid)).get();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void save(UserKey u) {
        userKeyRepository.save(u);
    }
}
