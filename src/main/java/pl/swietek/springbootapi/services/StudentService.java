package pl.swietek.springbootapi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.swietek.springbootapi.models.Student;
import pl.swietek.springbootapi.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Spring bean - > Component/Service = injectable
//@Component
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository
                .findById(id)
                .orElse(null);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentAlreadyExists = studentRepository.findStudentByEmail(student.getEmail());
        if( studentAlreadyExists.isPresent() ) throw new IllegalStateException("User Already Exists - Email taken");
        studentRepository.save(student);
        return student;
    }

    public boolean deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if ( !exists ) throw new IllegalStateException("User with id: " + id  + " Does Not Exists - Can't perform Delete Action");
        studentRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Student updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User Does Not exist"));


        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentAlreadyExists = studentRepository.findStudentByEmail(email);
            if (studentAlreadyExists.isPresent()) {
                throw new IllegalStateException("User Already Exists - Email taken");
            }
            student.setEmail(email);
        }

        return studentRepository.save(student);
    }
}
