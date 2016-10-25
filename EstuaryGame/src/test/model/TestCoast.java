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
	int minsize = 5;
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
		
	}

}
