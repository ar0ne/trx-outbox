package org.example.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.exception.DataProcessingException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SerializationHelper {

    private final ObjectMapper objectMapper;

    public SerializationHelper() {
        this.objectMapper = JsonMapper.builder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public String toJson(Object object) {
        if (isNull(object)) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new DataProcessingException("Failed to serialize to Json", e);
        }
    }

    public <T> T fromJson(String value, Class<T> clazz) {
        if (isNull(value)) {
            return null;
        }

        try {
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new DataProcessingException("Failed to deserialize from Json", e);
        }
    }

}
