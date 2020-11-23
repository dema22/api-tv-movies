package com.example.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/* We created a JacksonConfiguration class, we will setup ObjectMapper there,
so Jackson can correctly serialize and deserialize classes containing JsonNullable wrapped fields.

We use @PostConstruct annotation so we can configure Jackson’s ObjectMapper after Spring Boot,
because we don’t want to lose all useful setup made previously by Spring Boot, such as Date and
time serialization.
*/

@Configuration
public class JacksonConfiguration {
    private final ObjectMapper objectMapper;

    public JacksonConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    ObjectMapper jacksonObjectMapper() {
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }
}
