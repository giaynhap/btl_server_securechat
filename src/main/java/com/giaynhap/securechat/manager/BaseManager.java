package com.giaynhap.securechat.manager;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.giaynhap.securechat.service.ImageServiceImpl;
import com.giaynhap.securechat.service.serviceInterface.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseManager {
    protected final Logger LOG ;

    @Autowired
    ConversationService conversationService;
    @Autowired
    UserKeyService userKeyService;
    @Autowired
    ContactService contactService;
    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    OtpService otpService;
    @Autowired
    BCrypt.Hasher bHasher;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RedisTemplate redisTemplate;

    public BaseManager() {
        LOG = LoggerFactory.getLogger(getClass());
        LOG.info("Initializing..");
    }
}
