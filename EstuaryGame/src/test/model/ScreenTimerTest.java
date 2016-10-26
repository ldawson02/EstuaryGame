package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eScreenTimerState;
import model.ScreenTimer;

public class ScreenTimerTest {

	ScreenTimer timer;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		timer = new ScreenTimer();
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		timer.setState(eScreenTimerState.OFF);
	}

	@After
	public void tearDown() throws Exception {
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
}