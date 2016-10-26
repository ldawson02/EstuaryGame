package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Remove;

public class RemoveTest {
	
	Remove removepower;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		removepower = new Remove();
		
	}

	@Test
	public void appearTest() {
		removepower.appear();
		assertNotNull(removepower.getPosX());
		assertNotNull(removepower.getPosY());
	}
	
	@Test
	public void floatingTest() {
		int posX = removepower.getPosX();
		int posY = removepower.getPosY();
		removepower.floating();
		assertTrue(removepower.getPosX() != posX);
		assertTrue(removepower.getPosY() != posY);
		
	}
	@Test
	public void catchingTest() {
		
	}
	
	@Test
	public void disappearTest() {
		removepower.disappear();
		assertNull(removepower);
		assertNull(removepower.getPosX());
		assertNull(removepower.getPosY());
		
	}


}