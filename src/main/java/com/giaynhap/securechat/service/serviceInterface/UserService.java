package com.giaynhap.securechat.service.serviceInterface;

import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.UserKey;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.mongodb.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getByUsername(String username);

    void cacheUser(UserInfoDTO user);
    @Nullable
    UserInfoDTO getUserCache(String uuid);
    UserInfo getUserInfo(String uuid);

    User save(User user, UserInfo userInfo, UserKey userKey);
    User save(User user);
    UserInfo save(UserInfo userInfo);
    UserKey save(UserKey userKey);

    List<UserInfo> findByName(String name);
}
