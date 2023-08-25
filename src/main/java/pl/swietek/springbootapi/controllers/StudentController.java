package pl.swietek.springbootapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.swietek.springbootapi.models.Student;
import pl.swietek.springbootapi.responses.common.ApiBasicResponse;
import pl.swietek.springbootapi.services.StudentService;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    // Autowired -tells its using deps injection
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity
                .ok()
                .body(studentService.getStudents()) ;
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") Long id) {
        Student student = studentService.getStudentById(id);

        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student){
        return ResponseEntity
                .ok()
                .body(studentService.addNewStudent(student));
    }

    @PutMapping(path="{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("studentId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        return ResponseEntity
                .ok()
                .body(studentService.updateStudent(id, name, email));
    }


    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity
                    .ok()
                    .body(new ApiBasicResponse(true, "Student Deleted"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiBasicResponse(false, "Student Not Found or Delete Failed"));
        }
    }
}
