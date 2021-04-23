package guru.sfg.beer.inventory.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {
    public static final String QUEUE_NEW_INVENTORY ="new-inventory";

    private final ObjectMapper objectMapper;

    /**
     * Bean -> message converter
     * Using Jackson libraries to convert message to JSON
     * ie Object -> To JSON String and vice versa, JSON String -> To Java Object
     * @return converter
     */
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setObjectMapper(objectMapper);

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

}
