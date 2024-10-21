package org.example.kafka.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static java.util.Objects.isNull;
import static org.example.constants.KafkaConstants.CLASS_NAME_HEADER;

public class KafkaValueDeserializer implements Deserializer<Object> {

    private final ObjectMapper objectMapper;

    public KafkaValueDeserializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return null;
    }

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data) {
        if (isNull(data)) {
            return null;
        }

        try {
            String json = new String(data, StandardCharsets.UTF_8);
            Class<?> classByTopic = getClassByTopic(headers);
            return objectMapper.readValue(json, classByTopic);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when deserializing byte[] to class");
        }
    }

    private Class<?> getClassByTopic(Headers headers) {
        Iterator<Header> classHeaders = headers.headers(CLASS_NAME_HEADER).iterator();

        if (!classHeaders.hasNext()) {
            return String.class;
        }

        try {
            return Class.forName(new String(classHeaders.next().value()));
        } catch (ClassNotFoundException e) {
            return String.class;
        }
    }
}
