package com.giaynhap.securechat.utils.serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Long timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        jsonGenerator.writeNumber(timestamp);
    }

}

