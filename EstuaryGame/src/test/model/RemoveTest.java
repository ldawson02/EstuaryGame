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
	static Remove remove3;
	static Coast coast;
	static GameController gc;
	static ActiveItems itemslist;
	
	static Debris d2;
	static Debris d3;
	static Debris d4;
	static Debris d5;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		remove1 = new Remove(5,10);
		remove2 = new Remove(300,400);
		remove1.setState(eFloaterState.MOVING);
		remove2.setState(eFloaterState.MOVING);
		gc = new GameController(new EstuaryGame());
		gc.getItems().getAllPowers().add(new Remove(40,20));
		gc.getItems().getAllPowers().add(new Remove(30,20));
	
		
		remove3 = new Remove(200,300);
		d2 = new Debris(100,200);
		d2.setState(eFloaterState.RESTING);
		d3 = new Debris(300,200);
		d3.setState(eFloaterState.MOVING);
		d4 = new Debris(100,50);
		d4.setState(eFloaterState.RESTING);
		d5 = new Debris(50,200);
		d5.setState(eFloaterState.RESTING);
		itemslist = new ActiveItems();
		itemslist.addDebris(d2);
		itemslist.addDebris(d3);
		itemslist.addDebris(d4);
		itemslist.addDebris(d5);
		
		
	}


	@Test
	public void testCatching() {
		assertEquals(remove1.getState(), eFloaterState.MOVING);
		remove1.catching();
		assertEquals(remove1.getState(), eFloaterState.LIFTED);
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
		for(Debris d: debris){
			assertEquals(d.getState(), eFloaterState.RESTING);
		}
		
		
		
	}
	
	@Test
	public void powerTest(){
		assertEquals(itemslist.getAllDebris().size(),4);
		Remove.power(itemslist);
		for(Debris d: itemslist.getAllDebris()){
			System.out.println(d.getState());
			assertTrue(d.getState() != eFloaterState.RESTING);
		}
	}
	
	
	
	

}