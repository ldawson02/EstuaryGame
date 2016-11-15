package test.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Gabions;
import model.HealthBar;
import model.Wall;

public class WallsTest {
	
	public Wall wall1;
	public Wall wall2;
	public HealthBar healthBar;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		/*
		wall1 = new Wall(10,20);
		wall2 = new Wall(5,5);
		wall1.setHealth(0);
		wall2.setHealth(0);
		healthBar.setHealth(100);
		*/
	}
	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void buildTest() {
		wall1.build();
		assertEquals(wall1.getHealth(), 100);
		assertEquals(wall1.getPosX(), 10);
		assertEquals(wall1.getPosY(), 20);
		assertEquals(wall1.getDecayTime(), 0);
		wall2.build();
		assertEquals(wall2.getHealth(), 100);
		assertEquals(wall2.getPosX(), 5);
		assertEquals(wall2.getPosY(), 5);
		assertEquals(wall2.getDecayTime(), 0);
		
	}
	
	@Test
	public void decayTest() {
		wall2.decay(8);
		assertEquals(wall2.getDecayTime(), 8);
		assertEquals(wall2.getHealth(), 68);
		wall2.decay(25);
		assertEquals(wall2.getDecayTime(), 25);
		assertEquals(wall2.getHealth(), 0);
	}
	
	@Test
	public void healthBarTest() {
		wall1.decay(10);
		assertEquals(wall1.getDecayTime(), 10);
		assertEquals(wall1.getHealth(), 60);
		wall1.updateHealthBar();
		assertEquals(healthBar.getHealth(), 96);
		wall2.updateHealthBar();
		assertEquals(healthBar.getHealth(), 86);
	}
	
	
	@Test
	public void crumbleTest() {
		wall1.crumble();
		assertNull(wall1);
		wall2.crumble();
		assertNull(wall2);
		
	}


}
