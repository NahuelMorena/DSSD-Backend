package dssd.global.furniture.backend.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CloudApiConfig {
    @Bean
    public RestTemplateBuilder restTemplate() {
        return new RestTemplateBuilder();
    }
    
}
