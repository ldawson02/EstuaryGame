package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		timer.setState("off");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFreeze() {
		assertEquals(timer.getState(), "off");
		timer.freeze();
		assertEquals(timer.getState(), "frozen");
	}
	
	@Test
	public void testContinue() {
		assertEquals(timer.getState(), "off");
		timer.Continue();
		assertEquals(timer.getState(), "on");
	}
	
	@Test
	public void testEndGame() {
		timer.setState("on");
		assertEquals(timer.getState(), "on");
		timer.endGame();
		assertEquals(timer.getState(), "off");
	}
}
