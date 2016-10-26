package test.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Coast;
import model.Gabions;

public class GabionsTest {

	public Gabions gabion1;
	public Gabions gabion2;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		gabion1 = new Gabions(10,20);
		gabion2 = new Gabions(5,5);
		gabion1.setHealth(0);
		gabion2.setHealth(0);
		
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void buildTest() {
		gabion1.build();
		assertEquals(gabion1.getHealth(), 100);
		assertEquals(gabion1.getPosX(), 10);
		assertEquals(gabion1.getPosY(), 20);
		assertEquals(gabion1.getDecayTime(), 0);
		gabion2.build();
		assertEquals(gabion2.getHealth(), 100);
		assertEquals(gabion2.getPosX(), 5);
		assertEquals(gabion2.getPosY(), 5);
		assertEquals(gabion2.getDecayTime(), 0);
		
	}
	
	@Test
	public void decayTest() {
		gabion1.decay(20);
		assertEquals(gabion1.getDecayTime(), 20);
		assertEquals(gabion1.getHealth(), 50);
		gabion1.decay(40);
		assertEquals(gabion1.getDecayTime(), 40);
		assertEquals(gabion1.getHealth(), 0);
		gabion2.decay(10);
		assertEquals(gabion2.getDecayTime(), 10);
		assertEquals(gabion2.getHealth(), 75);
		gabion2.decay(30);
		assertEquals(gabion2.getDecayTime(), 30);
		assertEquals(gabion2.getHealth(), 25);
	}
	
	@Test
	public void crumbleTest() {
		gabion1.crumble();
		assertNull(gabion1);
		gabion2.crumble();
		assertNull(gabion2);
		
	}


}
