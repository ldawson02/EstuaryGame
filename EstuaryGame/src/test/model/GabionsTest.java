package test.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Coast;
import model.Gabions;
import model.HealthBar;

public class GabionsTest {

	public Gabions gabion1;
	public Gabions gabion2;
	public HealthBar healthbar;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		gabion1 = new Gabions(10, 0);
		gabion2 = new Gabions(5, 5);
		gabion1.setHealth(0);
		gabion2.setHealth(0);
		healthbar.setHealth(100);
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
		gabion2.decay(10);
		assertEquals(gabion2.getDecayTime(), 10);
		assertEquals(gabion2.getHealth(), 75);
		gabion2.decay(30);
		assertEquals(gabion2.getDecayTime(), 30);
		assertEquals(gabion2.getHealth(), 25);
	}
	
	@Test
	public void healthBarTest(){
		gabion1.decay(20);
		assertEquals(gabion1.getDecayTime(), 20);
		assertEquals(gabion1.getHealth(), 50);
		gabion1.updateHealthBar();
		assertEquals(healthbar.getHealth(), 95);
		gabion1.decay(40);
		assertEquals(gabion1.getDecayTime(), 40);
		assertEquals(gabion1.getHealth(), 0);
		gabion1.updateHealthBar();
		assertEquals(healthbar.getHealth(), 90);
		
	}
	
	
	@Test
	public void crumbleTest() {
		gabion1.crumble();
		assertNull(gabion1);
		gabion2.crumble();
		assertNull(gabion2);
		
	}
	


}
