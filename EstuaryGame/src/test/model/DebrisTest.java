package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eFloaterState;
import eNums.eDebrisType;
import eNums.eThrowDirection;
import model.CoastL;
import model.CoastR;
import model.Debris;

public class DebrisTest {

	//TODO: Technically, the coasts and the debris need to be connected
	//through the GameController
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
		trash.setState(eFloaterState.MOVING);
		recyc.setState(eFloaterState.MOVING);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRest() {
		assertEquals(trash.getState(), eFloaterState.MOVING);
		trash.rest();
		assertEquals(trash.getState(), eFloaterState.RESTING);
	}
	
	/**
	@Test
	public void testThrowTrashCorrect() {
		//Empty coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
		//Throw it correctly
		trash.setState(eFloaterState.LIFTED);
		trash.setCorrectBin(eDebrisType.TRASH);
		trash.throwDebris(trash.getCorrectBin());
		assertEquals(eThrowDirection.LEFT.getDirection(), eDebrisType.TRASH.getType());
		//Should not build up the coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
	}
	/**
	@Test
	public void testThrowTrashIncorrect() {
		//Empty coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
		//Throw it correctly
		trash.setState(eDebrisState.LIFTED);
		trash.throwDebris(eThrowDirection.RIGHT);
		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.RECYCLING.getType());
		//Should build up the right coast
		assertEquals(coastR.getBuildUp().count(), 1);
		assertEquals(coastL.getBuildUp().count(), 0);
	}
	
	@Test
	public void testThrowRecyclingCorrect() {
		//Empty coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
		//Throw it correctly
		recyc.setState(eDebrisState.LIFTED);
		recyc.throwDebris(eThrowDirection.RIGHT);
		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.RECYCLING.getType());
		//Should not build up the coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
	}
	
	@Test
	public void testThrowRecyclingIncorrect() {
		//Empty coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
		//Throw it correctly
		recyc.setState(eDebrisState.LIFTED);
		recyc.throwDebris(eThrowDirection.LEFT);
		assertEquals(eThrowDirection.LEFT.getDirection(), eDebrisType.TRASH.getType());
		//Should build up the left coast
		assertEquals(coastL.getBuildUp().count(), 1);
		assertEquals(coastR.getBuildUp().count(), 0);
	}
	
	@Test
	public void testCorrectBin() {
		assertTrue(trash.correctBin(eThrowDirection.LEFT));
		assertTrue(recyc.correctBin(eThrowDirection.RIGHT));
		assertFalse(trash.correctBin(eThrowDirection.RIGHT));
		assertFalse(recyc.correctBin(eThrowDirection.LEFT));
	}
	*/
}

