package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.DrawableItem;

public class DrawableItemTest {

	@Test
	public void moveTest() {
		
		DrawableItem item1 = new DrawableItem(100,300,5,4);
		item1.setStartandEnd(5,8,400,500);
		item1.setDelayOff();
		item1.move();
		assertTrue(item1.getPosX() != 5);
		assertTrue(item1.getPosY() != 8);
		while(item1.getPosX()<400 || item1.getPosY()<500){
			item1.move();
		}
		assertTrue(item1.getPosX()>400);
		assertTrue(item1.getPosY()>500);
		
		item1.move();
		assertTrue(item1.getPosX() == 5);
		assertTrue(item1.getPosY() == 8);
	}

}
