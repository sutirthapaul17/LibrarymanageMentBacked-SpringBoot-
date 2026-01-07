package com.example.LibraryManagement.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class EndpointLogger {

    @Bean
    public CommandLineRunner logEndpoints(ApplicationContext context) {
        return args -> {
            RequestMappingHandlerMapping mapping =
                    context.getBean(RequestMappingHandlerMapping.class);

            System.out.println("\n======= AVAILABLE API ENDPOINTS =======");

            mapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println(
                        key.getMethodsCondition() + " " +
                                key.getPatternValues()
                );
            });

            System.out.println("=======================================\n");
        };
    }
}
