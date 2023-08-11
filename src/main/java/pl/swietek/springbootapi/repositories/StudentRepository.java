package pl.swietek.springbootapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.swietek.springbootapi.models.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

//    @Query("SELECT s FROM Student s WHERE s.email = ?1") <- takie zapytanie leci
    Optional<Student>findStudentByEmail(String email);
}
