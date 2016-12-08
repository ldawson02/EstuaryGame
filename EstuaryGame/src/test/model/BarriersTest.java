
package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eBarrierType;
import eNums.eDebrisType;
import model.Barriers;
import model.Bin;
import model.Player;
import view.EstuaryGame;


public class BarriersTest {
	static GameController gc;
	static Barriers b1;
	static Barriers b2;
	static Barriers b3;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gc = new GameController(new EstuaryGame());
		b1 = new Barriers(100,60, eBarrierType.Gabion);
		b2 = new Barriers(200, 60, eBarrierType.Wall);
		b3 = new Barriers(300, 60);
	}
	
	@Test
	public void BarrierTypeTest(){
		assertEquals(b1.getType(), eBarrierType.Gabion);
		assertEquals(b2.getType(), eBarrierType.Wall);
		assertEquals(b3.getType(), eBarrierType.EMPTY);
		b3.setType(eBarrierType.Gabion);
		assertEquals(b3.getType(), eBarrierType.Gabion);
		
		assertEquals(b1.getHeight(), 37);
		assertEquals(b1.getWidth(), 45);
		
		
	}
	
	@Test
	public void erodeTest(){
		b1.erode();
		assertEquals(b1.getType(), eBarrierType.EMPTY);
		b2.erode();
		assertEquals(b2.getType(), eBarrierType.EMPTY);
		
	}
	
	@Test
	public void setupLeftCoastTest(){
		ArrayList<Barriers> leftbarriers = Barriers.setUpLeftCoast();
		assertEquals(leftbarriers.size(),5);
		int i=0;
		for(Barriers b: leftbarriers){
			assertEquals(b.getPosY(),b.getBarrierY());
			assertEquals(b.getPosX(), b.getLeftEdge()+50*i);
			i++;
		}
		
	}
	
	@Test
	public void setupRightCoastTest(){
		ArrayList<Barriers> rightbarriers = Barriers.setUpRightCoast();
		assertEquals(rightbarriers.size(),5);
		int i=0;
		for(Barriers b: rightbarriers){
			assertEquals(b.getPosY(),b.getBarrierY());
			assertEquals(b.getPosX(), (b.getRightEdge()-200)+50*i);
			i++;
		}
		
	}	

}
