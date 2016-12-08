package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eBarrierType;
import eNums.eCoastState;
import model.Barriers;
import model.Coast;
import view.EstuaryGame;

public class CoastTest {
	
	static Coast coast;
	static Coast coast2;
	static Coast coast3;
	static Coast coast4;
	static GameController gc;
	static int erosionRate = 1;
	static ArrayList<Barriers> leftbarriers;
	static ArrayList<Barriers> rightbarriers;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		coast = new Coast();
		Barriers b1 = new Barriers(100,200, eBarrierType.Gabion);
		Barriers b2 = new Barriers(150,200, eBarrierType.Wall);
		Barriers b3 = new Barriers(200,200, eBarrierType.EMPTY);
		coast2 = new Coast(100,200, b1);
		coast3 = new Coast(100,200, b2);
		coast4 = new Coast(100,200, b3);
		coast.setErosionRate(erosionRate);
		gc = new GameController(new EstuaryGame());
		leftbarriers = Barriers.setUpLeftCoast();
		rightbarriers = Barriers.setUpLeftCoast();
		
		
	}

	@Test
	public void testCoastErodeBarrier(){
		assertTrue(coast2.isProtected());
		assertEquals(coast2.getState(), eCoastState.NO_HIT);
		coast2.erode();
		assertEquals(coast2.getState(), eCoastState.ONE_HIT);
		
		assertTrue(coast3.isProtected());
		assertEquals(coast3.getState(), eCoastState.NO_HIT);
		coast3.erode();
		assertEquals(coast3.getState(), eCoastState.ONE_HIT);
	}
	
	@Test
	public void testCoastErodeNoBarrier(){
		assertFalse(coast4.isProtected());
		assertEquals(coast4.getState(), eCoastState.NO_HIT);
		coast4.erode();
		assertEquals(coast4.getState(), eCoastState.ONE_HIT);
		coast4.erode();
		assertEquals(coast4.getState(), eCoastState.TWO_HIT);
		coast4.erode();
		assertEquals(coast4.getState(), eCoastState.ERODED);
		
	}
	
	@Test 
	public void testChangeErosionRate() {
		//Should not have changed yet
		assertTrue(coast.getErosionRate() == erosionRate);
		//Test a positive change
		coast.changeErosionRate(100);
		assertTrue(coast.getErosionRate() == erosionRate + 100);
		//Test a negative change
		coast.setErosionRate(erosionRate);
		coast.changeErosionRate(-5);
		assertTrue(coast.getErosionRate() == erosionRate - 5);
	}
	
	@Test
	public void setupLeftCoastTest(){
		ArrayList<Coast> leftcoast = Coast.setUpLeftCoast(leftbarriers);
		assertEquals(leftcoast.size(),5);
		int i=0;
		for(Coast c: leftcoast){
			assertEquals(c.getPosY(),Barriers.getBarrierY()-30);
			assertEquals(c.getPosX(), leftbarriers.get(i).getPosX());
			assertEquals(c.getBarrier(), leftbarriers.get(i));
			assertEquals(c.getCoastID(), i+1);
			i++;
		}
		
	}
	
	@Test
	public void setupRightCoastTest(){
		ArrayList<Coast> rightcoast = Coast.setUpRightCoast(rightbarriers);
		assertEquals(rightcoast.size(),5);
		int i=0;
		for(Coast c: rightcoast){
			assertEquals(c.getPosY(),Barriers.getBarrierY()-30);
			assertEquals(c.getPosX(), rightbarriers.get(i).getPosX());
			assertEquals(c.getBarrier(), rightbarriers.get(i));
			assertEquals(c.getCoastID(), i+6);
			i++;
		}
		
	}
	
	

}
