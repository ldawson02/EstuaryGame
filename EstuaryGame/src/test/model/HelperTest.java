package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHelperState;
import model.Debris;
import model.Helper;
import model.Remove;

public class HelperTest {
	
	static Helper helper;
	static Remove remove1;
	static Debris debris1;
	static Debris debris2;
	static Debris debris3;
	static Debris debris4;
	static Debris debris5;
	static ArrayList<Debris> debris;
	static Helper helper2;
	Helper helper3;

	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		remove1 = new Remove();
		helper = new Helper();
		helper.updatePos(20, 10);
		debris1 = new Debris(eDebrisType.RECYCLING);
		debris1.updatePos(50,50);
		debris1.setState(eFloaterState.RESTING);
		debris2 = new Debris(eDebrisType.RECYCLING);
		debris2.updatePos(600,30);
		debris2.setState(eFloaterState.RESTING);
		debris3 = new Debris(eDebrisType.TRASH);
		debris3.updatePos(500,40);
		debris2.setState(eFloaterState.RESTING);
		debris4 = new Debris(eDebrisType.TRASH);
		debris4.updatePos(400,20);
		debris4.setState(eFloaterState.RESTING);
		
		debris = new ArrayList<Debris>();
		debris.add(debris1);
		debris.add(debris2);
		debris.add(debris3);
		helper2 = new Helper();
		
	}

	
	@Test
	public void getIsRightTest() {
		assertTrue(helper.isRight());
	}
	@Test
	public void nextStateTest(){
		helper.checkOffScreen();
		assertEquals(helper.getState(), eHelperState.WALKING);
		helper.checkState();
		assertEquals(helper.getState(), eHelperState.PICKING_UP);
		helper.nextState();
		assertEquals(helper.getState(), eHelperState.HOLDING);
		helper.nextState();
		assertEquals(helper.getState(), eHelperState.WALKING_OFF);
	}
	
	@Test
	public void checkStateTest(){
		helper2.setState(eHelperState.HOLDING);
		helper2.checkState();
		assertEquals(helper2.getState(), eHelperState.HOLDING);
		helper2.setTimeInStage(eHelperState.HOLDING.getTimeLimit());
		helper2.checkState();
		assertEquals(helper2.getState(), eHelperState.WALKING_OFF);
	}
	
	@Test
	public void finalYTest(){
		helper3 = new Helper();
		helper3.setFinalY(debris);
		assertEquals(helper3.getFinalY(), 30);
		
		debris.add(debris4);
		
		helper3.setFinalY(debris);
		assertEquals(helper3.getFinalY(), 20);
		
	}
	
	@Test
	public void addTimeTest(){
		helper.setState(eHelperState.WALKING);
		int initialtime = helper.getTimeInStage();
		helper.addTime(10);
		assertEquals(helper.getTimeInStage(), 0);
		
	}

}
