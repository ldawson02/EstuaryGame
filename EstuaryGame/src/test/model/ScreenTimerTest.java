package test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eScreenTimerState;
import model.ScreenTimer;
import view.EstuaryGame;

public class ScreenTimerTest {

	static ScreenTimer timer;
	static GameController gc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timer = new ScreenTimer();
		gc = new GameController(new EstuaryGame());
	}

	@Before
	public void setUp() throws Exception {
		timer.setState(eScreenTimerState.OFF);
	}
	@Test
	public void testFreeze() {
		assertEquals(timer.getState(), eScreenTimerState.OFF);
		timer.freeze();
		assertEquals(timer.getState(), eScreenTimerState.FROZEN);
	}
	
	@Test
	public void testContinue() {
		assertEquals(timer.getState(), eScreenTimerState.OFF);
		timer.Continue();
		assertEquals(timer.getState(), eScreenTimerState.ON);
	}
	
	@Test
	public void testEndGame() {
		timer.setState(eScreenTimerState.ON);
		assertEquals(timer.getState(), eScreenTimerState.ON);
		timer.endGame();
		assertEquals(timer.getState(), eScreenTimerState.OFF);
	}
	
	@Test
	public void testUpdateRemaining() {
		timer.setElapsedTime(10000);
		timer.updateRemaining();
		assertEquals(timer.getTimeRemaining(), timer.getMaxTime() - timer.getElapsedTime());
		timer.setElapsedTime(timer.getMaxTime()+1000);
		timer.updateRemaining();
		assertTrue(timer.getState() == eScreenTimerState.OFF);
		timer.start();
		timer.setElapsedTime(timer.getMaxTime());
		timer.updateRemaining();
		assertTrue(timer.getState() == eScreenTimerState.OFF);
		
	}
	
	@Test
	public void testGameTimer(){
		assertNotNull(gc.getItems().getScreenTimer());
		assertTrue(gc.getItems().getScreenTimer().getState() == eScreenTimerState.ON);
	
	}

}
