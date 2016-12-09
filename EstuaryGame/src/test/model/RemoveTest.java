package test.model;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Collisions;
import controller.GameController;
import controller.GameController.PowerInitiate;
import controller.GameController.ThrowChoice;
import controller.GameController.ThrowChosen;
import controller.GameController.spawnDebris;
import controller.GameController.spawnPowers;
import eNums.eFloaterState;
import model.Barriers;
import model.Coast;
import model.Debris;
import model.Player;
import model.Powers;
import model.Rebuild;
import model.Remove;
import view.EstuaryGame;

public class RemoveTest {

	static Remove remove1;
	static Remove remove2;
	static Coast coast;
	static GameController gc;
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		remove1 = new Remove(5,10);
		remove2 = new Remove(300,400);
		gc = new GameController(new EstuaryGame());
		remove1.setState(eFloaterState.MOVING);
		remove2.setState(eFloaterState.MOVING);
		gc.getItems().getAllPowers().add(new Remove(40,20));
		gc.getItems().getAllPowers().add(new Remove(30,20));
	}


	@Test
	public void testCatching() {
		assertEquals(remove1.getState(), eFloaterState.MOVING);
		remove1.catching();
		assertEquals(remove1.getState(), eFloaterState.LIFTED);
	}
	@Test
	public void testSpawnPowers() throws InterruptedException {
		Thread.sleep(10000);
		assertFalse(gc.getItems().getAllPowers().isEmpty());
	}
	
	
	@Test
	public void testCaughtandInitiated() throws InterruptedException{
		Collisions collision = new Collisions();
		Remove p = remove2;
		int p_xpos = p.getPosX();
		int p_ypos = p.getPosY();
		gc.getMainPlayer().updatePos(p_xpos, p_ypos);
		assertTrue(collision.checkCollision(gc.getMainPlayer(),p));
		Thread.sleep(500);
		p.catching();
		assertEquals(p.getState(), eFloaterState.LIFTED);
		

		PowerInitiate action1 = gc.new PowerInitiate(p);
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		
		assertEquals(p.getState(), eFloaterState.INITIATED);
		spawnPowers action2 = gc.new spawnPowers();
		action2.actionPerformed(new ActionEvent(action2, ActionEvent.ACTION_PERFORMED, null){});
		
		
		Thread.sleep(500);
		ArrayList<Debris> debris = gc.getItems().getRestingDebris();
		ArrayList<Debris> debris2 = gc.getItems().getAllDebris();
		System.out.println("DEBRIS SIZE" + debris.size());
		System.out.println("DEBRIS SIZE2" + debris2.size());
		for(Debris d: debris2){
			System.out.println(d.getState());
		}
		//assertEquals(debris.size(), 0);
		
		
	}
	
	
	
	

}