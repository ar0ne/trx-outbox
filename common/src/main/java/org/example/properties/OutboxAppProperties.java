package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "outbox")
@Data
public class OutboxAppProperties {
    private Long pullOutboxEventsRate;
    private Integer batchSize;
    private KafkaDefaults kafkaDefaults;
}
