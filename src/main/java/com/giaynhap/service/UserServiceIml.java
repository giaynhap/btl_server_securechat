package com.giaynhap.service;

import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import com.giaynhap.repository.UserInfoRepository;
import com.giaynhap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Component
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<Users> getAllUser() {
        return (List<Users> )userRepository.findAll();
    }

    public Users getUser(String uuid){
       return (Users)userRepository.findById(uuid).get();
    }
    @Override
    public Users login(String account, String password){
        return userRepository.selectByUserNamePassword(account,password);
    }
    @Override
    public UserInfo getUserInfo(String uuid) {
        return userInfoRepository.findById(uuid).get();
    }

    @Override
    public List<UserInfo> findByName(String name) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return userInfoRepository.findByName(name,pageRequest).getContent();
    }

    @Override
    public void addUser(String account, String password, String name) {
        Users u = new Users();
        u.setUUID(UUID.randomUUID().toString());
        u.setAccount(account);
        u.setPassword(password);
        u.setName(name);
        userRepository.save(u);
    }
    @Override
    public Users addUser(Users u) {

        u.setUUID(UUID.randomUUID().toString());

        return userRepository.save(u);
    }

    @Override
    public List<UserInfo> getRandomSuggest(String uuid) {
        return userInfoRepository.getRandomSuggest(uuid);
    }

    public UserInfo addUserInfo(UserInfo info){
        return userInfoRepository.save(info);
    }

	@Override
	public UserInfo findByUserName(String username){
        try {
            Users user = userRepository.findByUserName(username);
            if (user == null || user.getUserInfo() == null) {
                return null;
            }
            return user.getUserInfo();
        }catch (Exception e){
            return null;
        }

	}

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }


}
