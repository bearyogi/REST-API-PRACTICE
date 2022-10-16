package com.example.learning.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepozytory studentRepozytory) {
        return args -> {
           Student adam = new Student(
                    "Adam",
                    LocalDate.of(1970, Month.APRIL,30),
                    "adam.nowak@gmail.com"
            );
            Student monika = new Student(
                    "Monika",
                    LocalDate.of(1980, Month.JANUARY,5),
                    "monika.grab@gmail.com"
            );

            studentRepozytory.saveAll(
                    List.of(adam,monika)
            );
        };
    }
}
