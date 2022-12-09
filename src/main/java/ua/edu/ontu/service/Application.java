package ua.edu.ontu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var argsHandler = new ArgsHandler().handleArgsIfExists(args);

		if (argsHandler.canContinue()) {
			SpringApplication.run(Application.class, args);
		}
	}
}
