package hr.project;

import hr.project.model.Smjer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import hr.project.repository.*;
import java.util.Arrays;


@SpringBootApplication
public class Quiz4Students2Application {

	public static void main(String[] args) {
		SpringApplication.run(Quiz4Students2Application.class, args);
	}


	@Bean
	CommandLineRunner init(SmjerRepository smjerRepository) {
		return (evt) -> Arrays.asList(1,2,3,4).forEach(
						a -> {
							Smjer smjer = smjerRepository.save(new Smjer(a+100, "nazivjjj"));
							System.out.println(a + " " + smjer.getId() + " " + smjer.getNaziv() );
						});
	}
}

