package com.rewards.fetch.fetchPoints.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointsServiceTest {

	@Autowired
	private PointsService pointsServiceImpl;
	

	@Test
	public void testAddPoinsMethod() {
		String result =pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		
		assertEquals("Success", result);
	}
	
	@Test
	public void testDeductPoinsMethod() {
		pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		String result =pointsServiceImpl.duductPoints("Surendra", 300);
		
		assertEquals("Success", result);
	}

}
