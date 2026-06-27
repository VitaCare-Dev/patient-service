package com.grupo10.patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque del microservicio de gestión de pacientes.
 *
 * <p>Este microservicio expone una API REST para administrar pacientes,
 * sus direcciones, enfermedades crónicas y umbrales médicos personalizados.
 */
@SpringBootApplication
public class PatientServiceApplication {

	/**
	 * Punto de entrada de la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}

}
