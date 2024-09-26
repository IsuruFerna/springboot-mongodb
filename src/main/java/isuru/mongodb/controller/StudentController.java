package isuru.mongodb.controller;

import isuru.mongodb.entities.Student;
import isuru.mongodb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }
}
