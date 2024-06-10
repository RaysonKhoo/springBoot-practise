package com.example.demo.config;


import com.example.demo.repository.Student;
import com.example.demo.repository.StudentResInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentResInterface repository) {
        return args ->{
//            com.example.demo.repository.Student example = new com.example.demo.repository.Student(
//                    1,
//                    "hi",
//                    "Hello.gmail.com",
//                    LocalDate.of(2000, Month.JANUARY,5),
//                    5




//            );
//            com.example.demo.repository.Student halo = new com.example.demo.repository.Student(
//                    2,
//                    "hello",
//                    "Hello.gmail.com",
//                    LocalDate.of(2000, Month.JANUARY,5),
//                    4
//
//
//            );
//            repository.saveAll(
//                    List.of(example,halo)
//            );
        };
    }
}
