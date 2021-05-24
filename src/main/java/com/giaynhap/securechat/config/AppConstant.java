package com.giaynhap.securechat.config;


public class AppConstant {
    public static final String SUCCESS_MESSAGE = "success";
    public static final String  ERROR_MESSAGE = "failed";
    public static final String BAD_REQUEST_MESSAGE = "bad reqeuest";
    public static final long JWT_TOKEN_VALIDITY = 1000*60*60*5;
    public static final String SC_UNAUTHORIZED = "UNAUTHORIZED";

    public enum CacheKeys{
        USERINFO(0),
        CONVERSATION(1);
        private final int value;
        private CacheKeys(int value) {
            this.value = value;
        }
    }
    public enum MessageCommand{
        MESSAGE(0),
        READ(1),
        TYPING(2),
        BLOCK(3);

        private final int value;
        private MessageCommand(int value) {
            this.value = value;
        }
    }
    public enum SocketLoginCommand{
        PREHANDSHAKE(0),
        HANDSHAKE(1),
        TRANSFERDATA(2);
        private final int value;
        private SocketLoginCommand(int value) {
            this.value = value;
        }
    }
}
