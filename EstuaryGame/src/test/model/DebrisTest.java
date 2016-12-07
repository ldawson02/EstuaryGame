package test.model;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Desktop.Action;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.KeyStroke;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Collisions;
import controller.GameController;
import controller.GameController.ThrowChoice;
import controller.GameController.ThrowChosen;
import controller.GameController.spawnDebris;
import controller.MovementController;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eThrowDirection;
import model.Coast;
import model.Debris;
import model.Player;
import model.Rebuild;
import view.EstuaryGame;

public class DebrisTest {

	//TODO: Technically, the coasts and the debris need to be connected
	//through the GameController
	static Debris trash;
	static Debris recyc;
	static Coast coast;
	static GameController gc;
	static Debris randomDebris1;
	static Debris randomDebris2;
	static Debris d1;
	static Debris d2;
	static Debris d3;
	static Debris d4;
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trash = new Debris(eDebrisType.TRASH);
		recyc = new Debris(eDebrisType.RECYCLING);
		coast = new Coast();
		gc = new GameController(new EstuaryGame());
		trash.setState(eFloaterState.MOVING);
		recyc.setState(eFloaterState.MOVING);
		d1 = new Debris(300,10);
		d1.setType(eDebrisType.TRASH);
		d2 = new Debris(200,300);
		d2.setType(eDebrisType.TRASH);
		gc.getItems().getAllDebris().add(d1);
		gc.getItems().getAllDebris().add(d2);
		d3 = new Debris(700,350);
		d3.setType(eDebrisType.RECYCLING);
		d4 = new Debris(300,40);
		d4.setType(eDebrisType.RECYCLING);
		gc.getItems().getAllDebris().add(d3);
		gc.getItems().getAllDebris().add(d4);
	}


	@Test
	public void testRest() {
		assertEquals(trash.getState(), eFloaterState.MOVING);
		trash.rest();
		assertEquals(trash.getState(), eFloaterState.RESTING);
	}
	@Test
	public void testSpawnDebris() throws InterruptedException {
		Thread.sleep(1000);
		spawnDebris spawn1 = gc.new spawnDebris();
		assertFalse(gc.getItems().getAllDebris().isEmpty());
	}
	
	@Test
	public void testCaughtandThrownCorrectTrash() throws InterruptedException, AWTException{
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = d1;
		MovementController.move(d);
		int d_xpos = d.getPosX();
		int d_ypos = d.getPosY();
		gc.getMainPlayer().updatePos(d_xpos, d_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),d));
		Thread.sleep(500);
		d.catching();
		assertEquals(d.getState(), eFloaterState.LIFTED);
		

		ThrowChoice action1 = gc.new ThrowChoice(eThrowDirection.RIGHT, d);
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		//ThrowChosen
		ThrowChosen action2 = gc.new ThrowChosen(d);
		action2.actionPerformed(new ActionEvent(action2, ActionEvent.ACTION_PERFORMED, null){});
			
		assertEquals(d.getState(), eFloaterState.THROWING);
		
		spawnDebris action3 = gc.new spawnDebris();
		action3.actionPerformed(new ActionEvent(action3, ActionEvent.ACTION_PERFORMED, null){});
		
		Thread.sleep(2000);
		assertEquals(d.getState(), eFloaterState.RESTING);
		//Resting on a bin
		assertTrue(d.getPosY()>140 && d.getPosY()<160);
		
		
	}
	
	@Test
	public void testCaughtandThrownCorrectRecycling() throws InterruptedException{
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = d3;
		MovementController.move(d);
		int d_xpos = d.getPosX();
		int d_ypos = d.getPosY();
		gc.getMainPlayer().updatePos(d_xpos, d_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),d));
		Thread.sleep(500);
		d.catching();
		assertEquals(d.getState(), eFloaterState.LIFTED);
		
		
		ThrowChoice action1 = gc.new ThrowChoice(eThrowDirection.LEFT, d);
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		//ThrowChosen
		ThrowChosen action2 = gc.new ThrowChosen(d);
		action2.actionPerformed(new ActionEvent(action2, ActionEvent.ACTION_PERFORMED, null){});
		System.out.println(d.getState());
			
		assertEquals(d.getState(), eFloaterState.THROWING);
			
		
		
		spawnDebris action3 = gc.new spawnDebris();
		action3.actionPerformed(new ActionEvent(action3, ActionEvent.ACTION_PERFORMED, null){});
		
		Thread.sleep(2000);
		assertEquals(d.getState(), eFloaterState.RESTING);
		//Resting on a bin
		assertTrue(d.getPosY()>140 && d.getPosY()<160);
		
	}
	@Test
	public void testCaughtandThrownIncorrectTrash() throws InterruptedException, AWTException{
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = d2;
		MovementController.move(d);
		int d_xpos = d.getPosX();
		int d_ypos = d.getPosY();
		gc.getMainPlayer().updatePos(d_xpos, d_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),d));
		Thread.sleep(500);
		d.catching();
		assertEquals(d.getState(), eFloaterState.LIFTED);
		
		
		ThrowChoice action1 = gc.new ThrowChoice(eThrowDirection.LEFT, d);
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		//ThrowChosen
		ThrowChosen action2 = gc.new ThrowChosen(d);
		action2.actionPerformed(new ActionEvent(action2, ActionEvent.ACTION_PERFORMED, null){});
		assertEquals(d.getState(), eFloaterState.THROWING);
		
		spawnDebris action3 = gc.new spawnDebris();
		action3.actionPerformed(new ActionEvent(action3, ActionEvent.ACTION_PERFORMED, null){});
		Thread.sleep(2000);
		assertEquals(d.getState(), eFloaterState.RESTING);
		//Resting on a coast
		assertTrue(d.getPosY()>300);
		assertTrue(d.getPosX()<200 || d.getPosX()>600);
		
		
	}
	
	@Test
	public void testCaughtandThrownIncorrectRecycle() throws InterruptedException{
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = d4;
		MovementController.move(d);
		int d_xpos = d.getPosX();
		int d_ypos = d.getPosY();
		gc.getMainPlayer().updatePos(d_xpos, d_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),d));
		Thread.sleep(500);
		d.catching();
		assertEquals(d.getState(), eFloaterState.LIFTED);
		ThrowChoice action1 = gc.new ThrowChoice(eThrowDirection.RIGHT, d);
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		//ThrowChosen
		ThrowChosen action2 = gc.new ThrowChosen(d);
		action2.actionPerformed(new ActionEvent(action2, ActionEvent.ACTION_PERFORMED, null){});
			
		assertEquals(d.getState(), eFloaterState.THROWING);
		
		spawnDebris action3 = gc.new spawnDebris();
		action3.actionPerformed(new ActionEvent(action3, ActionEvent.ACTION_PERFORMED, null){});
		Thread.sleep(2000);
		assertEquals(d.getState(), eFloaterState.RESTING);
		//Resting on a coast
		assertTrue(d.getPosY()>300);
		assertTrue(d.getPosX()<200 || d.getPosX()>600);
			
		
		
	}
	
	
	@Test
	public void testSetType(){
		randomDebris1 = new Debris(4,2);
		randomDebris2 = new Debris(5,6);
		assertEquals(randomDebris1.getPosX(), 4);
		assertEquals(randomDebris1.getPosY(), 2);
		assertEquals(randomDebris2.getPosX(), 5);
		assertEquals(randomDebris2.getPosY(), 6);
		
		randomDebris1.setType(eDebrisType.TRASH);
		randomDebris2.setType(eDebrisType.RECYCLING);
		assertEquals(randomDebris1.getType(), eDebrisType.TRASH);
		assertEquals(randomDebris2.getType(), eDebrisType.RECYCLING);
		
		
		
	}
	
	@Test
	public void testGetandSetThrowDirection(){
		trash.setThrowDirection(eThrowDirection.RIGHT);
		assertEquals(trash.getThrowDirection(), eThrowDirection.RIGHT);
		
		recyc.setThrowDirection(eThrowDirection.LEFT);
		assertEquals(recyc.getThrowDirection(), eThrowDirection.LEFT);
		
	}
	

}

