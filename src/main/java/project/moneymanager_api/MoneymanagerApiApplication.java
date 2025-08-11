package project.moneymanager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneymanagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneymanagerApiApplication.class, args);
	}

}
