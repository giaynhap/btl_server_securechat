package com.giaynhap.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       Long timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        gen.writeString(timestamp+"");
    }
}