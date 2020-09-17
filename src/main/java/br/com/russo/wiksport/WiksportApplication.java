package br.com.russo.wiksport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class WiksportApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiksportApplication.class, args);
	}

}
