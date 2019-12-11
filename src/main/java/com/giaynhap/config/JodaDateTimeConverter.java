package com.giaynhap.config;

import javax.persistence.AttributeConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JodaDateTimeConverter implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime localDateTime) {
        Long timestamp = null;
        if (localDateTime != null) {
            timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        return timestamp;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long timestamp) {
        LocalDateTime localDateTime = null;
        if (timestamp != null) {
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }
        return localDateTime;
    }
}