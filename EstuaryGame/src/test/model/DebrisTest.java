package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisState;
import eNums.eDebrisType;
import model.CoastL;
import model.CoastR;
import model.Debris;

public class DebrisTest {

	Debris trash;
	Debris recyc;
	CoastL coastL;
	CoastR coastR;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		trash = new Debris(eDebrisType.TRASH);
		recyc = new Debris(eDebrisType.RECYCLING);
		coastL = new CoastL();
		coastR = new CoastR();
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRest() {
		assertEquals(trash.getState(), eDebrisState.MOVING);
		trash.rest();
		assertEquals(trash.getState(), eDebrisState.RESTING);
	}
	
	@Test
	public void testThrow() {
		
	}

}
