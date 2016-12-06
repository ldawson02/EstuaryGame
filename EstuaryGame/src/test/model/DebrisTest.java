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
	public void testCaughtandThrownCorrectDebris() throws InterruptedException, AWTException{
		Thread.sleep(10000);
		
		ArrayList<Debris> debris = gc.getItems().getAllDebris();
		Collisions collision = new Collisions();
		Debris d = debris.get(0);
		for(Debris deb: debris){
			if(deb.getState() == eFloaterState.MOVING){
				d = deb;
				break;
			}
		}
		int d_xpos = d.getPosX();
		int d_ypos = d.getPosY();
		gc.getMainPlayer().updatePos(d_xpos, d_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),d));
		Thread.sleep(500);
		assertEquals(d.getState(), eFloaterState.LIFTED);
		
		Robot robot = new Robot();
		if(d.getType() == eDebrisType.RECYCLING){
			/*robot.keyPress(KeyEvent.VK_LEFT);
			Thread.sleep(250);
			robot.keyRelease(KeyEvent.VK_LEFT);
			Thread.sleep(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(250);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(250);*/
			
			//controller.GameController.ThrowChoice action = new controller.GameController.ThrowChoice(eThrowDirection.LEFT,d);
			//action.actionPerformed(new ActionEvent(, ActionEvent.ACTION_PERFORMED, null){});
			//ThrowChosen
			//System.out.println(d.getState());
			//assertEquals(d.getState(), eFloaterState.THROWING);
			
		}
		if(d.getType() == eDebrisType.TRASH){
			/**robot.keyPress(KeyEvent.VK_RIGHT);
			Thread.sleep(250);
			robot.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(250);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(250);**/
			//System.out.println(d.getState());
			//assertEquals(d.getState(), eFloaterState.THROWING);
		}
		
		
	}
	
	@Test
	public void testThrowTrashCorrect() {
		trash.catching();
		trash.setCorrectBin(eDebrisType.TRASH);
		trash.throwDebris(trash.getCorrectBin());	
		assertEquals(trash.getState(), eFloaterState.THROWING);

		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.TRASH.getType());
		
		
	}
	
	/**@Test
	public void testThrowTrashIncorrect() {
		trash.catching();
		trash.throwDebris(eThrowDirection.RIGHT);
		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.RECYCLING.getType());
		
	}
	
	@Test
	public void testThrowRecyclingCorrect() {
		
		//Throw it correctly
		recyc.setState(eDebrisState.LIFTED);
		recyc.throwDebris(eThrowDirection.RIGHT);
		assertEquals(eThrowDirection.RIGHT.getDirection(), eDebrisType.RECYCLING.getType());
		//Should not build up the coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
	}
	
	@Test
	public void testThrowRecyclingIncorrect() {
		//Empty coasts
		assertEquals(coastL.getBuildUp().count(), 0);
		assertEquals(coastR.getBuildUp().count(), 0);
		//Throw it correctly
		recyc.setState(eDebrisState.LIFTED);
		recyc.throwDebris(eThrowDirection.LEFT);
		assertEquals(eThrowDirection.LEFT.getDirection(), eDebrisType.TRASH.getType());
		//Should build up the left coast
		assertEquals(coastL.getBuildUp().count(), 1);
		assertEquals(coastR.getBuildUp().count(), 0);
	}
	
	@Test
	public void testCorrectBin() {
		assertTrue(trash.correctBin(eThrowDirection.LEFT));
		assertTrue(recyc.correctBin(eThrowDirection.RIGHT));
		assertFalse(trash.correctBin(eThrowDirection.RIGHT));
		assertFalse(recyc.correctBin(eThrowDirection.LEFT));
	}**/
}

