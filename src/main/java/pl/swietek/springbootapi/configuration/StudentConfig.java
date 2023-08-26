package pl.swietek.springbootapi.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.swietek.springbootapi.models.Student;
import pl.swietek.springbootapi.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            List<Student> mockStudents = createMockStudents();
            repository.saveAll(mockStudents);
        };
    }

    private List<Student> createMockStudents() {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Student student = new Student(
                    "Student" + (i + 1),
                    LocalDate.of(2000 + i, Month.JANUARY, 1),
                    "student" + (i + 1) + "@example.com"
            );
            students.add(student);
        }

        return students;
    }
}
