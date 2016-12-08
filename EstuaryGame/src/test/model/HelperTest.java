package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eHelperState;
import model.Debris;
import model.Helper;
import model.Remove;

public class HelperTest {
	
	static Helper helper;
	static Remove remove1;
	static Debris debris1;
	static ArrayList<Debris> debris;

	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		remove1 = new Remove();
		helper = new Helper(remove1);
		helper.updatePos(20, 10);
		debris1 = new Debris(20,30);
		debris = new ArrayList<Debris>();
		debris.add(debris1);
	}

	@Test
	public void getPowerTest() {
		assertEquals(helper.getPower(), remove1);
	}
	
	@Test
	public void getIsRightTest() {
		assertTrue(helper.isRight());
	}
	@Test
	public void nextStateTest(){
		helper.nextState();
		assertEquals(helper.getState(), eHelperState.PICKING_UP);
		helper.nextState();
		assertEquals(helper.getState(), eHelperState.HOLDING);
		helper.nextState();
		assertEquals(helper.getState(), eHelperState.WALKING_OFF);
	}
	
	@Test
	public void addTimeTest(){
		helper.setState(eHelperState.WALKING);
		int initialtime = helper.getTimeInStage();
		helper.addTime(10);
		assertEquals(helper.getTimeInStage(), 0);
		
	}

}
