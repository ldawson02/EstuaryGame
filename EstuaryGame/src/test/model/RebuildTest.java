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
import eNums.eBarrierType;
import eNums.eFloaterState;
import model.Barriers;
import model.Coast;
import model.Debris;
import model.Player;
import model.Powers;
import model.Rebuild;
import view.EstuaryGame;

public class RebuildTest {

	static Rebuild rebuild1;
	static Rebuild rebuild2;
	static Coast coast;
	static GameController gc;
	static Barriers b1;
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rebuild1 = new Rebuild(5,10);
		rebuild2 = new Rebuild(2,4);
		gc = new GameController(new EstuaryGame());
		rebuild1.setState(eFloaterState.MOVING);
		rebuild2.setState(eFloaterState.MOVING);
		gc.getItems().getAllPowers().add(new Rebuild(40,20));
		gc.getItems().getAllPowers().add(new Rebuild(30,20));
		b1 = new Barriers(200,300);
		
	}


	@Test
	public void testCatching() {
		assertEquals(rebuild1.getState(), eFloaterState.MOVING);
		rebuild1.catching();
		assertEquals(rebuild1.getState(), eFloaterState.LIFTED);
	}
	@Test
	public void testSpawnPowers() throws InterruptedException {
		Thread.sleep(10000);
		assertFalse(gc.getItems().getAllPowers().isEmpty());
	}
	
	
	@Test
	public void testCaughtandInitiated() throws InterruptedException{
		Thread.sleep(5000);
		ArrayList<Powers>  powers = gc.getItems().getAllPowers();
		Collisions collision = new Collisions();
		Rebuild p = rebuild2;
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
		
		
		Thread.sleep(10000);
		
		ArrayList<Barriers> barriers = gc.getItems().getAllBarriers();
		assertTrue(barriers.size() >= 5);
		
		
		
	}
	
	@Test
	public void powerTest(){
		Rebuild.power(b1);
		assertEquals(b1.getType(), eBarrierType.Gabion);
	}
	
	
	
	

}