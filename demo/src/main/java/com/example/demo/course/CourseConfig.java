package com.example.demo.course;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseConfig {

    @Bean
    CommandLineRunner commandLineRunner1(CourseRepository repository) {
        return args -> {
            Course c1 = new Course(
                    "IT0001",
                    1);

            Course c2 = new Course(
                    "IT0002",
                    4);

            Course c3 = new Course(
                    "IT0003",
                    2);

            Course c4 = new Course(
                    "IT0004",
                    3);

            repository.saveAll(
                    List.of(c1, c2, c3, c4));

        };
    }
}
