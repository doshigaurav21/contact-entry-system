package com.gaurav;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsApplication.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Contacts application is started...");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
