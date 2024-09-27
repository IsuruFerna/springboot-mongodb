package isuru.mongodb;

import isuru.mongodb.entities.Address;
import isuru.mongodb.entities.Gender;
import isuru.mongodb.entities.Student;
import isuru.mongodb.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("England", "London", "NE");
			String email = "bob@example.com";
			Student student = new Student("Bob", "Ferna", Gender.MALE, 30, email, address, List.of("Math", "Science"), LocalDateTime.now());

			// usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println(s + " already exists");
			}, () -> {
				System.out.println("Inserting student!");
				repository.insert(student);
			});
		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size()> 1) {
			throw new IllegalStateException("Found many students with email " + email);
		}

		if(students.isEmpty()) {
			System.out.println("Inserting student!");
			repository.insert(student);
		} else  {
			System.out.println(student + " already exists");
		}
	}

}
