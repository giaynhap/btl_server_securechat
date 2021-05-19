package com.giaynhap.securechat.service.serviceInterface;

import com.giaynhap.securechat.model.UserKey;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface UserKeyService {
    public UserKey getKey(String uuid);
    public void save(UserKey u);

}
