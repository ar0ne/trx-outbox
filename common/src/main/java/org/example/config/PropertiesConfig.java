package org.example.config;

import org.example.properties.OutboxAppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        OutboxAppProperties.class
})
@Configuration
public class PropertiesConfig {

}
