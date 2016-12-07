package test.model;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import controller.GameController.HAction;
import controller.GameController.ThrowChoice;
import controller.GameController.VAction;
import eNums.eDebrisType;
import eNums.ePlayerState;
import eNums.eThrowDirection;
import model.Barriers;
import model.Debris;
import model.Player;
import view.EstuaryGame;

public class PlayerTest {

	public static ArrayList<Barriers> barriers;
	public static ArrayList<Debris> debris;
	public static GameController gc;
	static Player gamePlayer;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gc = new GameController(new EstuaryGame());
		gamePlayer = gc.getMainPlayer();
		
	}

	@Test
	public void testHorizontalRightMove() throws InterruptedException{
		int initialX = gamePlayer.getPosX();
		HAction action1 = gc.new HAction(gamePlayer.getSpeed());
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		assertEquals(gamePlayer.getPosX(), initialX + gamePlayer.getSpeed());
		
	}
	@Test
	public void testHorizontalLeftMove(){
		int initialX = gamePlayer.getPosX();
		HAction action1 = gc.new HAction(-gamePlayer.getSpeed());
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		assertEquals(gamePlayer.getPosX(), initialX - gamePlayer.getSpeed());
	}
	
	@Test
	public void testVerticalUpMove(){
		int initialY = gamePlayer.getPosY();
		VAction action1 = gc.new VAction(gamePlayer.getSpeed());
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		assertEquals(gamePlayer.getPosY(), initialY + gamePlayer.getSpeed());
	}
	
	@Test
	public void testVerticalDownMove(){
		int initialY = gamePlayer.getPosY();
		VAction action1 = gc.new VAction(-gamePlayer.getSpeed());
		action1.actionPerformed(new ActionEvent(action1, ActionEvent.ACTION_PERFORMED, null){});
		assertEquals(gamePlayer.getPosY(), initialY - gamePlayer.getSpeed());
	}
	
	@Test
	public void testPlayerHeight(){
		Player p = new Player();
		assertEquals(p.getState(), ePlayerState.Idle);
		assertEquals(p.getHeight(), p.idleHeight);
		p.setState(ePlayerState.Lifting);
		assertEquals(p.getHeight(), p.liftingHeight);
		
	}
	


}
