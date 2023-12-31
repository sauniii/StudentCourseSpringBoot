package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student sauni = new Student(
                    "Sauni",
                    "sauni@gmail.com",
                    "0112765198",
                    LocalDate.of(2000, Month.JANUARY, 5),
                    "address1"

            );

            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    "0773649517",
                    LocalDate.of(2004, Month.FEBRUARY, 5),
                    "address2"

            );

            repository.saveAll(
                    List.of(sauni, alex));

        };
    }

}
