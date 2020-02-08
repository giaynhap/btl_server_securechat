package com.giaynhap.service;


import com.giaynhap.model.UserInfo;
import com.giaynhap.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;


@Service
public
interface UserService{

    public List<Users> getAllUser();
    public void addUser(String  account, String password,String name);
    public Users login(String account, String password);
    public UserInfo getUserInfo(String uuid);
    public List<UserInfo> findByName(String name);
	public UserInfo findByUserName(String username);
	public void save(Users user);
    public Users addUser(Users u);
    public List<UserInfo> getRandomSuggest(String uuid);
    public UserInfo addUserInfo(UserInfo info);
    
}
