package com.recruitment;

import com.recruitment.initializer.DataLoader;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Recruitment API",
                version = "1.0",
                description = "API documentation for the recruitment platform"
        )
)
public class RecruiteApplication {

    public static void main ( String[] args ) {
        SpringApplication.run(RecruiteApplication.class , args);
    }

    @Bean
    public CommandLineRunner loadData ( DataLoader apiService ) {
        return args -> {
            try {
                apiService.authenticateAndLoadData();
            } catch (Exception e) {
                System.err.println("Error during data loading: " + e.getMessage());
                e.printStackTrace();
            }
        };

    }
}
