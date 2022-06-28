package com.kuda.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Profile("test")
@TestPropertySource("classpath:application-test.properties")
class KudaApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
