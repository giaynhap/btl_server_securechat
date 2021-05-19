package com.giaynhap.securechat;

import com.giaynhap.securechat.repository.UserRepository;
import com.giaynhap.securechat.service.FilterService;
import com.giaynhap.securechat.service.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@EnableWebSocket
@EnableCaching
@SpringBootApplication
public class SecureChatServerApplication implements CommandLineRunner {

    @Autowired
    FilterService filterService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(SecureChatServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("SecureChatServerApplication run command ");
        filterService.config();

    }
}
