package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Rebuild;
import model.Remove;

public class RebuildTest {
	
	Rebuild rebuildpower;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		rebuildpower = new Rebuild(0,4);
	}

	@Test
	public void appearTest() {
		rebuildpower.appear();
		assertNotNull(rebuildpower.getPosX());
		assertNotNull(rebuildpower.getPosY());
	}
	
	@Test
	public void floatingTest() {
		int posX = rebuildpower.getPosX();
		int posY = rebuildpower.getPosY();
		rebuildpower.floating();
		assertTrue(rebuildpower.getPosX() != posX);
		assertTrue(rebuildpower.getPosY() != posY);
		
	}
	@Test
	public void catchingTest() {
		
	}
	
	@Test
	public void disappearTest() {
		rebuildpower.disappear();
		assertNull(rebuildpower);
		assertNull(rebuildpower.getPosX());
		assertNull(rebuildpower.getPosY());
		
	}

}
