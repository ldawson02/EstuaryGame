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
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eThrowDirection;
import model.Coast;
import model.Debris;
import model.Player;
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
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trash = new Debris(eDebrisType.TRASH);
		recyc = new Debris(eDebrisType.RECYCLING);
		coast = new Coast();
		gc = new GameController(new EstuaryGame());
		trash.setState(eFloaterState.MOVING);
		recyc.setState(eFloaterState.MOVING);
	}


	@Test
	public void testRest() {
		assertEquals(trash.getState(), eFloaterState.MOVING);
		trash.rest();
		assertEquals(trash.getState(), eFloaterState.RESTING);
	}
	@Test
	public void testSpawnDebris() throws InterruptedException {
		Thread.sleep(10000);
		assertFalse(gc.getItems().getAllDebris().isEmpty());
	}
	@Test
	public void testRestingDebris(){
		//gc.getItems()
	}
	
	@Test
	public void testCaughtandThrownCorrectTrash() throws InterruptedException, AWTException{
		Thread.sleep(5000);
		
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = debris.get(0);
		for(Debris deb: debris){
			if(deb.getState() == eFloaterState.MOVING && deb.getType() == eDebrisType.TRASH){
				d = deb;
				break;
			}
		}
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
		System.out.println("d POSITION: " + d.getPosY());
		assertTrue(d.getPosY()==150);
		
		
	}
	
	@Test
	public void testCaughtandThrownCorrectRecycling() throws InterruptedException{
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = debris.get(0);
		for(Debris deb: debris){
			if(deb.getState() == eFloaterState.MOVING && deb.getType() == eDebrisType.RECYCLING){
				d = deb;
				break;
			}
		}
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
		assertTrue(d.getPosY()==150);
		
	}
	@Test
	public void testCaughtandThrownIncorrectTrash() throws InterruptedException, AWTException{
		Thread.sleep(5000);
		
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = debris.get(0);
		for(Debris deb: debris){
			if(deb.getState() == eFloaterState.MOVING && deb.getType() == eDebrisType.TRASH){
				d = deb;
				break;
			}
		}
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
		Thread.sleep(5000);
		
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = debris.get(0);
		for(Debris deb: debris){
			if(deb.getState() == eFloaterState.MOVING && deb.getType() == eDebrisType.RECYCLING){
				d = deb;
				break;
			}
		}
		
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
	public void testThrowTrashCorrect() {
		trash.catching();
		trash.setCorrectBin(eDebrisType.TRASH);
		trash.throwDebris(trash.getCorrectBin());	
		assertEquals(trash.getState(), eFloaterState.THROWING);
		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.TRASH.getType());
		
		recyc.catching();
		recyc.setCorrectBin(eDebrisType.RECYCLING);
		recyc.throwDebris(recyc.getCorrectBin());	
		assertEquals(recyc.getState(), eFloaterState.THROWING);
		assertEquals(eThrowDirection.LEFT.getDirection(), eDebrisType.RECYCLING.getType());
		
		
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
	

}

