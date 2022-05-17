package org.wyk.swan;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableConfigurationProperties(ServerProperties.class)
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String... args){
        logger.info("Starting the Black Swan... Application");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}