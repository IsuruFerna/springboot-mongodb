package isuru.mongodb.repositories;

import isuru.mongodb.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findStudentByEmail(String email);

    @Query("{'firstName': ?0}")
    List<Student> findByFirstName(String firstName);

    @Query("{'firstName': ?0, 'lastName': ?1 }")
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    // useful when returning custom response(only the email)
    @Query(value = "{'firstName': ?0}", fields = "{'email':1}")
    List<Student> filterEmailResponseField(String firstName);

    // useful when returning custom response(exclude the email)
    @Query(value = "{'firstName': ?0}", fields = "{'email':0}")
    List<Student> removeEmailResponseField(String firstName);

    // case-sensitive: find by upper or lowercase
    @Query("{'firstName': {'$regex': ?0, '$options': 'i'} }")
    List<Student> findByNameIgnoreCaseSensitivity(String name);

    @Query("{'age':{'$gt': ?0, 'lt': ?1}")
    List<Student> findStudentByAgeBetween(int minAge, int maxAge);

}
