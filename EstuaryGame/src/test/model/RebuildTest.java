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

import controller.ActiveItems;
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
	static Rebuild rebuild3;
	static Coast coast;
	static GameController gc;
	static Barriers b1;
	static Barriers b2;
	static Barriers b3;
	static Barriers b4;
	static Barriers b5;
	static Barriers b6;
	static Barriers b7;
	static ArrayList<Barriers> barriers;
	static ActiveItems items1;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rebuild1 = new Rebuild(5,10);
		rebuild2 = new Rebuild(2,4);
		rebuild3 = new Rebuild(200,400);
		gc = new GameController(new EstuaryGame());
		rebuild1.setState(eFloaterState.MOVING);
		rebuild2.setState(eFloaterState.MOVING);
		rebuild3.setState(eFloaterState.INITIATED);
		gc.getItems().getAllPowers().add(new Rebuild(40,20));
		gc.getItems().getAllPowers().add(new Rebuild(30,20));
		b1 = new Barriers(200,300);
		
		b2 = new Barriers(300,300,eBarrierType.Gabion);
		b3 = new Barriers(400,300, eBarrierType.EMPTY);
		b4 = new Barriers(500,300, eBarrierType.Wall);
		b5 = new Barriers(600,300, eBarrierType.EMPTY);
		b6 = new Barriers(600,300, eBarrierType.EMPTY);
		b7 = new Barriers(600,300, eBarrierType.EMPTY);
		
		items1 = new ActiveItems();
		items1.addBarrier(b2);
		items1.addBarrier(b3);
		items1.addBarrier(b4);
		items1.addBarrier(b5);
		items1.addBarrier(b6);
		items1.addBarrier(b7);
		
		
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
	
	@Test
	public void getRebuildBarriersTest(){
		ArrayList<Barriers> rebuildBarriers = rebuild3.getRebuildBarriers(items1);
		assertEquals(rebuildBarriers.size(), 2);
		
		for(Barriers b: rebuildBarriers){
			assertEquals(b.getType(), eBarrierType.EMPTY);
		}
	}
	
	
	
	

}