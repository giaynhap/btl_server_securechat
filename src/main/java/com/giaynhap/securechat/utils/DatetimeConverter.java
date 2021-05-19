package com.giaynhap.securechat.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@WritingConverter
public class DatetimeConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime localDateTime) {
        Long timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return timestamp;
    }
}
