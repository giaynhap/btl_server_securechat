package com.giaynhap.service;

import com.giaynhap.model.UserKey;
import com.giaynhap.repository.UserKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserKeyServiceIml implements UserKeyService {
    @Autowired
    UserKeyRepository userKeyRepository;
    @Override
    public UserKey getKey(String uuid) {
        try {
            return userKeyRepository.findById(uuid).get();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void save(UserKey u) {
        userKeyRepository.save(u);
    }
}
