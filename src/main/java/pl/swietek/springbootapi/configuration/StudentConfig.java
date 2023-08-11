package pl.swietek.springbootapi.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.swietek.springbootapi.models.Student;
import pl.swietek.springbootapi.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
           Student student1 = new Student( "student",  LocalDate.of(2001, Month.FEBRUARY, 11),"user@user.com" );
           Student student2 = new Student( "student2", LocalDate.of(2001, Month.FEBRUARY, 11),"user@user.com" );

           repository.saveAll(
                   List.of(student1, student2)
           );
        };
    }
}
