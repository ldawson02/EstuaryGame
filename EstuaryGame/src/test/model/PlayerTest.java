package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Player;

public class PlayerTest {

	public Player p;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		p = new Player();
	}

	@Test
	public void testMove(){
		p.move();
		
	}
	
	@Test
	public void buildGabionTest() {
		
	}
	
	@Test
	public void buildWallTest(){
		
	}
	
	@Test
	public void pickUpDebrisTest(){
		
	}
	
	@Test
	public void throwDebrisTest(){
		
	}

}
