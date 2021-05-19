package com.giaynhap.securechat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class AppConfigure {
    public static AppConfigure shared ;
    @Value("${gn.avatar.path}")
    public String userAvatarPath;
    @Value("${gn.server.host}")
    public String serverHost;
    @Value("${gn.sticker.config}")
    public String stickerConfigPath;

    AppConfigure(){
        shared = this;
    }
}
