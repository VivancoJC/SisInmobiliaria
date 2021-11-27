package com.proyecto.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.proyecto.servidor.model")
@EnableJpaRepositories("com.proyecto.servidor.repository")
public class SisAlquilerInmueblesAdministradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisAlquilerInmueblesAdministradorApplication.class, args);
	}

}
