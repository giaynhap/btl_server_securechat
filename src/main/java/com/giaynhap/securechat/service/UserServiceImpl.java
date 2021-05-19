package com.giaynhap.securechat.service;

import com.giaynhap.securechat.config.AppConstant;
import com.giaynhap.securechat.model.User;
import com.giaynhap.securechat.model.UserInfo;
import com.giaynhap.securechat.model.UserKey;
import com.giaynhap.securechat.model.response.DTO.UserInfoDTO;
import com.giaynhap.securechat.repository.UserInfoRepository;
import com.giaynhap.securechat.repository.UserKeyRepository;
import com.giaynhap.securechat.repository.UserRepository;
import com.giaynhap.securechat.service.serviceInterface.UserService;
import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserKeyRepository userKeyRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUserName(username);
        return user;
    }

    @Override
    public void cacheUser(UserInfoDTO user) {
       try{
           redisTemplate.opsForHash().put(user.getId(), AppConstant.CacheKeys.USERINFO,user);
       } catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    @Nullable
    public UserInfoDTO getUserCache(String uuid) {
        try {
            Object o = redisTemplate.opsForHash().get(uuid, AppConstant.CacheKeys.USERINFO);
            if (o == null || !(o instanceof UserInfoDTO)) {
                return null;
            }
            return (UserInfoDTO)o;
        }catch ( Exception e){
            return  null;
        }
    }

    @Override
    @Nullable
    public UserInfo getUserInfo(String uuid) {
        Optional<UserInfo> u = userInfoRepository.findById(new ObjectId(uuid));
        if (!u.isPresent()){
            return  null;
        }
        return u.get();
    }

    @Override
    public User save(User user, UserInfo userInfo, UserKey userKey) {
        if (userKey != null) {
            userKeyRepository.save(userKey);
        }
        if (userInfo != null) {
            userInfo.setUserKey(userKey);
            userInfoRepository.save(userInfo);
        }

        user.setUserInfo(userInfo);
        return  userRepository.save(user);
    }



    @Override
    public User save(User user) {
        return  userRepository.save(user);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {

        return  userInfoRepository.save(userInfo);
    }

    @Override
    public UserKey save(UserKey userKey) {
        return userKeyRepository.save(userKey);
    }

    @Override
    public List<UserInfo> findByName(String name) {
        return userInfoRepository.findByName(name);
    }


}
