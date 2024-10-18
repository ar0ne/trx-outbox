package org.example.kafka;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import static java.util.Objects.isNull;
import static org.example.constants.KafkaConstants.CLASS_NAME_HEADER;

public class KafkaValueSerializer implements Serializer<Object> {

    private final ObjectMapper objectMapper;

    public KafkaValueSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return null;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        if (isNull(data)) {
            return null;
        }

        try {
            headers.add(CLASS_NAME_HEADER, data.getClass().getName().getBytes());
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when serializing MessageDto to byte[]");
        }
    }

}
