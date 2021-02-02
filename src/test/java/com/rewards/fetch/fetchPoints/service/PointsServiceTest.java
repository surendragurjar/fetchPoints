package com.rewards.fetch.fetchPoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rewards.fetch.fetchPoints.constants.PointsConstanst;
import com.rewards.fetch.fetchPoints.exception.PointsException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointsServiceTest {

	@Autowired
	private PointsService pointsServiceImpl;

	@Test
	public void testAddPointsMethod() {
		String result = pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		assertEquals("Success", result);
	}
	
	@Test
	public void testAddPoints_LowBalanceException() {
		pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		Throwable exception = assertThrows(PointsException.class,
				() -> pointsServiceImpl.addPoints("Surendra", "Marianos", -8000));
		assertEquals(PointsConstanst.LOW_BALANCE_MESSAGE, exception.getMessage());
	}

	@Test
	public void testDeductPointsMethod() {
		pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		String result = pointsServiceImpl.duductPoints("Surendra", 300);

		assertEquals("Success", result);
	}

	@Test
	public void testDeductPoints_LowBalanceException() {
		pointsServiceImpl.addPoints("Surendra", "Marianos", 500);
		Throwable exception = assertThrows(PointsException.class,
				() -> pointsServiceImpl.duductPoints("Surendra", 8000));
		assertEquals(PointsConstanst.LOW_BALANCE_MESSAGE, exception.getMessage());
	}
	
	@Test
	public void testDeductPoints_NegativePointsException() {
		Throwable exception = assertThrows(PointsException.class,
				() -> pointsServiceImpl.duductPoints("Surendra", -800));
		assertEquals(PointsConstanst.NEGATIVE_POINTS_MESSAGE, exception.getMessage());
	}

}
