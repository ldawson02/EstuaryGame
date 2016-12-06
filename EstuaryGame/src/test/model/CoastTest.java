package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import model.Coast;
import model.CoastR;

public class CoastTest {
	
	public static Coast coast;
	public static GameController gc;
	static int erosionRate = 1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		coast = new CoastR();
		coast.setErosionRate(erosionRate);
	}


	
	@Test 
	public void testChangeErosionRate() {
		//Should not have changed yet
		assertTrue(coast.getErosionRate() == erosionRate);
		//Test a positive change
		coast.changeErosionRate(100);
		assertTrue(coast.getErosionRate() == erosionRate + 100);
		//Test a negative change
		coast.setErosionRate(erosionRate);
		coast.changeErosionRate(-5);
		assertTrue(coast.getErosionRate() == erosionRate - 5);
	}

}
