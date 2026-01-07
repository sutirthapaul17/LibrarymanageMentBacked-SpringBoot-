package com.example.LibraryManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	// 🔥 LOG BASE URL + ALL API ENDPOINTS ON STARTUP
	@Bean
	public CommandLineRunner logAllEndpoints(
			ApplicationContext context,
			Environment environment
	) {
		return args -> {

			// ---- Base URL parts ----
			String port = environment.getProperty("server.port", "8080");
			String contextPath = environment.getProperty("server.servlet.context-path", "");
			String host = "http://localhost";

			String baseUrl = host + ":" + port + contextPath;

			System.out.println("\n======= BASE URL =======");
			System.out.println(baseUrl);

			// ---- Endpoints ----
			RequestMappingHandlerMapping mapping =
					context.getBean(RequestMappingHandlerMapping.class);

			System.out.println("\n======= AVAILABLE API ENDPOINTS =======");

			mapping.getHandlerMethods().forEach((key, value) -> {
				key.getMethodsCondition().getMethods().forEach(method -> {
					key.getPatternValues().forEach(pattern -> {
						System.out.println(
								"[" + method + "] " + baseUrl + pattern
						);
					});
				});
			});

			System.out.println("=======================================\n");
		};
	}
}
