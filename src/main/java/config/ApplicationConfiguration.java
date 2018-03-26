package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"config","Controller"})
public class ApplicationConfiguration {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfiguration.class, args);
	}
}
