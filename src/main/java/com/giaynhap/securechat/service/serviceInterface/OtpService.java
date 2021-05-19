package com.giaynhap.securechat.service.serviceInterface;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public interface OtpService {
    String genOTPCode(String secret) throws Exception;
    void sendOTP(String msisgn, String code);
    String genOTPCode(String secret, Long time) throws Exception ;

}
