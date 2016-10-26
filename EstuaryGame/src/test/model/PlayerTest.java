package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisType;
import model.Debris;
import model.Gabions;
import model.Player;
import model.Wall;

public class PlayerTest {

	public Player p;
	public ArrayList<Gabions> gabions;
	public ArrayList<Wall> walls;
	public ArrayList<Debris> debris;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		p = new Player();
		gabions.add(new Gabions(0,0));
		gabions.add(new Gabions(0,5));
		gabions.add(new Gabions(10,10));
		gabions.add(new Gabions(30,10));
		
		walls.add(new Wall(0,0));
		walls.add(new Wall(9,13));
		
		debris.add(new Debris(eDebrisType.RECYCLING));
		debris.add(new Debris(eDebrisType.TRASH));
		debris.add(new Debris(eDebrisType.TRASH));
		debris.add(new Debris(eDebrisType.RECYCLING));
		
	}

	@Test
	public void testMove(){
		int initialX = p.getPosX();
		int initialY = p.getPosY();
		
		p.move();
		int afterX = p.getPosX();
		int afterY = p.getPosY();
		
		assertFalse(initialX==afterX);
		assertFalse(initialY == afterY);
		
		p.move();
		
		assertFalse(afterX == p.getPosX());
		assertFalse(afterY == p.getPosY());
	}
	
	@Test
	public void buildGabionTest() {
		//This wouldn't actually work unless gabions was attached to the controller, these two pieces are disparate currently
		int initNumGabions = gabions.size();
		p.buildGabion();
		
		int afterNumGabions = gabions.size();
		
		assertTrue(initNumGabions == afterNumGabions - 1);
		
		p.buildGabion();
		
		assertTrue(afterNumGabions == gabions.size() - 1);
	}
	
	@Test
	public void buildWallTest(){
		//This wouldn't actually work unless gabions was attached to the controller, these two pieces are disparate currently
		int initNumWalls = walls.size();
		p.buildWall();

		int afterNumWalls = walls.size();

		assertTrue(initNumWalls == afterNumWalls - 1);

		p.buildWall();

		assertTrue(afterNumWalls == walls.size() - 1);
	}
	
	@Test
	public void pickUpDebrisTest(){
		int initNumDebris = debris.size();
		p.pickUpDebris();
		int afterNumDebris = debris.size();
		
		assertTrue(initNumDebris == afterNumDebris - 1);
		
		//probably check to see that the instance of the trash is null but idk
		
		p.pickUpDebris();
		
		assertTrue(afterNumDebris == debris.size() - 1);
	}
	
	@Test
	public void throwDebrisTest(){
		//This should test to make sure that the debris has been thrown??
		
		int initX = debris.get(0).getPosX();
		int initY = debris.get(0).getPosY();
		
		p.throwDebris(debris.get(0));
		
		assertFalse(initX == debris.get(0).getPosX());
		assertFalse(initY == debris.get(0).getPosY());
	}

}
