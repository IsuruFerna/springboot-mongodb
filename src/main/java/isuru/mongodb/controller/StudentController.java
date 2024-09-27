package isuru.mongodb.controller;

import isuru.mongodb.entities.Student;
import isuru.mongodb.repositories.StudentRepository;
import isuru.mongodb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/all")
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{firstName}")
    public List<Student> findByFirstName(@PathVariable(value = "firstName") String firstName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName));
        return mongoTemplate.find(query, Student.class);
    }

    @GetMapping("/{firstName}/{lastName}")
    public List<Student> findByFirstNameAndLastName(@PathVariable(value = "firstName") String firstName,
                                                    @PathVariable(value = "lastName") String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    // returns custom response (only the email)
    // make sure to add the property at application.properties: spring.jackson.default-property-inclusion=non_default
    @GetMapping("/customResponse/{firstName}")
    public List<Student> customResponse(@PathVariable(value = "firstName") String firstName) {
        return studentRepository.filterEmailResponseField(firstName);
    }

    // returns custom response (exclude the email)
    // make sure to add the property at application.properties: spring.jackson.default-property-inclusion=non_default
    @GetMapping("/customResponseRemoveEmail/{firstName}")
    public List<Student> removeEmailResponseField(@PathVariable(value = "firstName") String firstName) {
        return studentRepository.removeEmailResponseField(firstName);
    }

    // ignore case-sensitivity
    @GetMapping("/ignoreCase/{firstName}")
    public List<Student> findByNameIgnoreCaseSensitivity(@PathVariable (value = "firstName") String firstName) {
        return studentRepository.findByNameIgnoreCaseSensitivity(firstName);
    }

    @GetMapping("/ageBetween/{minAge}/{maxAge}")
    public List<Student> findStudentByAgeBetween(@PathVariable (value = "minAge") Integer minAge,
                                                 @PathVariable (value = "maxAge") Integer maxAge) {
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }
}
