package com.taller5.servidorJPA;

import com.taller5.servidorJPA.controller.PropertyContoller;
import com.taller5.servidorJPA.model.Property;
import com.taller5.servidorJPA.repository.PropertyRepository;
import com.taller5.servidorJPA.services.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServidorJpaApplicationTests {

	@Mock
	private PropertyRepository propertyRepository;

	@InjectMocks
	private PropertyService propertyService;

	@InjectMocks
	private PropertyContoller propertyController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		propertyService = new PropertyService(propertyRepository);
		propertyController = new PropertyContoller(propertyService);
	}

	@Test
	void getAllPropertiesReturnsAccepted() {
		List<Property> properties = Arrays.asList(new Property(), new Property());
		when(propertyService.getAllProperties()).thenReturn(properties);

		ResponseEntity<Object> response = propertyController.getProperties();

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	void getPropertyByAddressReturnsAccepted() {
		Property property = new Property();
		when(propertyService.getPropertyByAddress("address")).thenReturn(property);

		ResponseEntity<Object> response = propertyController.getPropertyByAddres("address");

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	void addPropertyReturnsAccepted() {
		Property property = new Property();
		when(propertyService.addProperty(property)).thenReturn(property);

		ResponseEntity<Object> response = propertyController.addProperty(property);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}


	@Test
	void getAllPropertiesReturnsNotFound() {
		when(propertyService.getAllProperties()).thenThrow(new RuntimeException("Error"));

		ResponseEntity<Object> response = propertyController.getProperties();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Error", response.getBody());
	}

	@Test
	void getPropertyByAddressReturnsNotFound() {
		when(propertyService.getPropertyByAddress("address")).thenThrow(new RuntimeException("Error"));

		ResponseEntity<Object> response = propertyController.getPropertyByAddres("address");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Error", response.getBody());
	}

	@Test
	void addPropertyReturnsExpectationFailed() {
		Property property = new Property();
		when(propertyService.addProperty(property)).thenThrow(new RuntimeException("Error"));

		ResponseEntity<Object> response = propertyController.addProperty(property);

		assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
		assertEquals("Error", response.getBody());
	}
}
