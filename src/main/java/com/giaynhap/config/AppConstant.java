package com.giaynhap.config;

public class AppConstant {
    public static final String SUCCESS_MESSAGE = "success";
    public static final String  ERROR_MESSAGE = "failed";
    public static final String BAD_REQUEST_MESSAGE = "bad reqeuest";
    public static final long JWT_TOKEN_VALIDITY = 1000*60*60*5;
    public static final String SC_UNAUTHORIZED = "UNAUTHORIZED";

    public enum MessageCommand{
            MESSAGE(0),
            READ(1),
            TYPING(2);

        private final int value;
        private MessageCommand(int value) {
            this.value = value;
        }
    }
}
