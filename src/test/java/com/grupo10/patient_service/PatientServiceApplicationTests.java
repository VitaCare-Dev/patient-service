package com.grupo10.patient_service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class PatientServiceApplicationTests {

	@Test
	void constructor_InstanciaClaseCorrectamente(){
		PatientServiceApplication app = new PatientServiceApplication();
		assertNotNull(app);
	}

	@Test
	void main_IniciandoAplicacionSpring(){
		try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
			PatientServiceApplication.main(new String[]{});
			mockedSpringApplication.verify(() -> SpringApplication.run(PatientServiceApplication.class, new String[]{}));
		}
	}



}
