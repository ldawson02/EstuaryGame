package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.AllDebris;
import model.Coast;

public class TestCoast {

	public Coast coast;
	int maxsize = 5;
	int minsize = 0;
	double erosionrate = 1.0;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		coast = new Coast();
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		coast.setMaxsize(maxsize);
		coast.setMinsize(minsize);
		coast.setSize(maxsize);
		coast.setBuildUp(new AllDebris());
		coast.setErosionRate(erosionrate);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testErode() {
		//Should start at max size
		assertEquals(coast.getSize(), maxsize);
		//Should decrease by 1
		coast.erode();
		assertEquals(coast.getSize(), maxsize - 1);
		//Should decrease again
		coast.erode();
		assertEquals(coast.getSize(), maxsize - 2);
		//Should be equal to minSize
		while (coast.getSize() != 0) {
			coast.erode();
		}
		assertEquals(coast.getSize(), minsize);
		//Size should not fall below minsize
		coast.erode();
		assertEquals(coast.getSize(), minsize);
	}
	
	@Test
	public void testRebuild() {
		//Start at min size
		coast.setSize(minsize);
		assertEquals(coast.getSize(), minsize);
		//Should increase by 1
		coast.rebuild();
		assertEquals(coast.getSize(), minsize + 1);
		//Should increase again
		coast.rebuild();
		assertEquals(coast.getSize(), minsize + 2);
		//Should be equal to minSize
		while (coast.getSize() != maxsize) {
			coast.rebuild();
		}
		assertEquals(coast.getSize(), maxsize);
		//Size should not increase above maxsize
		coast.rebuild();
		assertEquals(coast.getSize(), maxsize);
	}
	
	@Test 
	public void testChangeErosionRate() {
		//Should not have changed yet
		assertTrue(coast.getErosionRate() == erosionrate);
		//Test a positive change
		coast.changeErosionRate(1.0);
		assertTrue(coast.getErosionRate() == erosionrate + 1.0);
		//Test a negative change
		coast.setErosionRate(erosionrate);
		coast.changeErosionRate(-0.5);
		assertTrue(coast.getErosionRate() == erosionrate - 0.5);
	}
}
