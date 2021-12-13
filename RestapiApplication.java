package com.usterka.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class RestapiApplication {
	// TODO add a configuration -> BuildThenRun (with clean and package)
	public static void main(String[] args) {

		SpringApplication.run(RestapiApplication.class, args);
	}
}