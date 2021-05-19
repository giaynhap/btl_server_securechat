package com.giaynhap.securechat.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.giaynhap.securechat.manager.ContactManager;
import com.giaynhap.securechat.manager.ConversationManager;
import com.giaynhap.securechat.manager.UserKeyManager;
import com.giaynhap.securechat.manager.UserManager;
import com.giaynhap.securechat.model.Contact;
import com.giaynhap.securechat.service.serviceInterface.DeviceService;
import com.giaynhap.securechat.service.serviceInterface.UserService;
import com.giaynhap.securechat.utils.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    protected final Logger LOG ;

    @Autowired
    BCrypt.Hasher bHasher;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    UserManager userManager;
    @Autowired
    ContactManager contactManager;
    @Autowired
    UserKeyManager userKeyManager;
    @Autowired
    ConversationManager conversationManager;

    @Autowired
    ModelMapper modelMapper;
    BaseController(){
        LOG = LoggerFactory.getLogger(getClass());
        LOG.info("Initializing..");
    }

    boolean validateUsername(String userName){
        return true;
    }
    boolean validatePassword(String password){
        return true;
    }

}
