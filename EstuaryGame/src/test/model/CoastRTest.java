package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.CoastR;

public class CoastRTest {

	public CoastR coast;
	int maxsize = 5;
	int minsize = 0;
	int erosionrate = 1;
	
	//tentative: based on positions
	//right coast  (all positions are X)
	int minposition = 10;
	int positiondif = 20;
	int maxposition = minposition + positiondif*maxsize;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		coast = new CoastR();
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	/**@Before
	public void setUp() throws Exception {
		coast.setMaxsize(maxsize);
		coast.setMinsize(minsize);
		coast.setSize(maxsize);
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
		coast.changeErosionRate(1);
		assertTrue(coast.getErosionRate() == erosionrate + 1);
		//Test a negative change
		coast.setErosionRate(erosionrate);
		coast.changeErosionRate(-5);
		assertTrue(coast.getErosionRate() == erosionrate - 5);
	}	
	
	@Test
	public void updateCoordsTest() {
		//Starts at maxsize -> so maxposition
		assertEquals(coast.getPosX(), maxposition);
		coast.erode();
		//Erode once, move position
		assertEquals(coast.getPosX(), maxposition - positiondif);
		//Try building
		while (coast.getSize() != 0) {
			coast.erode();
		}
		assertEquals(coast.getPosX(), minposition);
		coast.rebuild();
		assertEquals(coast.getPosX(), maxposition);
	}**/
}
