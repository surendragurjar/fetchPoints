package com.rewards.fetch.fetchPoints.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class PointsServiceTest {
	
	@Autowired
	private PointsServiceImpl pointsService;
	
	@Test
	public void testAddPoinsMethod() {
		
		pointsService.addPoints("Surendra", "Marianos", 500);
		System.out.println(pointsService.lstTransactions.size());
	}

}
