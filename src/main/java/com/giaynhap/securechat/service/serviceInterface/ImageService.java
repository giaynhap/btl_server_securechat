package com.giaynhap.securechat.service.serviceInterface;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public interface ImageService {
    BufferedImage cropAvatar(BufferedImage input);
    BufferedImage resize(BufferedImage input,int width,int height);
    BufferedImage avatarLetterImage(String name);
}
