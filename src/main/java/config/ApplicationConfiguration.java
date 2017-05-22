package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan({"handlerservice","config","commons","cacheservice"})
public class ApplicationConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfiguration.class, args);
	}

}
