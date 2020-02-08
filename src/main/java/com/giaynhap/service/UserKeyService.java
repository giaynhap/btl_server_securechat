package com.giaynhap.service;

import com.giaynhap.model.UserKey;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface UserKeyService {
    public UserKey getKey(String uuid);
    public void save(UserKey u);

}
