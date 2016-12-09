package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.StormVisual;

public class StormVisualTest {

	@Test
	public void moveTest() {
		StormVisual s1 = new StormVisual();
		assertEquals(s1.getPosX(),-250);
		assertEquals(s1.getPosY(), 600);
		s1.move();
		assertEquals(s1.getPosX(),-250+s1.getSpeedX());
		assertEquals(s1.getPosY(), 600-s1.getSpeedY());
		
		
	}

}
