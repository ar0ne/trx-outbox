package org.example.properties;

import lombok.Data;

@Data
public class KafkaDefaults {
    private String topicName;
    private Integer topicReplicationFactor;
    private Integer topicPartitionsNumber;
    private Integer pollTimeout;
    private Integer lingerMs;
}
