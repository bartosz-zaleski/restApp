package com.charter.restApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class RestAppApplicationTests {

	@Test
	void test_calculate() {
		assertEquals(90, PointsCalculator.calculate(12000));
		assertEquals(50, PointsCalculator.calculate(10000));
		assertEquals(52, PointsCalculator.calculate(10100));
		assertEquals(0, PointsCalculator.calculate(5000));
		assertEquals(1, PointsCalculator.calculate(5100));
		assertEquals(0, PointsCalculator.calculate(4900));
		assertEquals(0, PointsCalculator.calculate(0));
		assertEquals(0, PointsCalculator.calculate(1));
		assertEquals(0, PointsCalculator.calculate(-1));
		assertEquals(0, PointsCalculator.calculate(-120));

		assertEquals(52, PointsCalculator.calculate(10150));
		assertEquals(49, PointsCalculator.calculate(9999));

		assertEquals(0, PointsCalculator.calculate(1));
	}
}

