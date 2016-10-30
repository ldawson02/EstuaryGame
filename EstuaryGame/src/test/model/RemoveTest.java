package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Remove;

public class RemoveTest {
	
	Remove removepower;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		removepower = new Remove(4,5);
		
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
		//did the right action get performed when the item was caught
	}
	
	@Test
	public void disappearTest() {
		removepower.disappear();
		assertNull(removepower);
		assertNull(removepower.getPosX());
		assertNull(removepower.getPosY());
		
	}


}
