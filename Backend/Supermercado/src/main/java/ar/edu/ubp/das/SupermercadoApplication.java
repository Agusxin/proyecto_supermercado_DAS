package ar.edu.ubp.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class SupermercadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermercadoApplication.class, args);
	}

}
