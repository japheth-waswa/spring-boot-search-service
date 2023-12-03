package com.japethwaswa.springelasticsearch;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchApplication {

	public static Dotenv dotenv;

	public static void main(String[] args) {
		dotenv = Dotenv.configure().load();
		SpringApplication.run(ElasticSearchApplication.class, args);
	}

}
