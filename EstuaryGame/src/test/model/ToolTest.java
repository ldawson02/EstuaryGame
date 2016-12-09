package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.ActiveItems;
import eNums.eBarrierType;
import model.Barriers;
import model.Coast;
import model.Tool;

public class ToolTest {
	static Barriers b1;
	static Barriers b2;
	static Barriers b3;
	static Barriers b4;
	static Barriers b5;
	static Barriers b6;
	static Barriers b7;
	static ArrayList<Barriers> barriers;
	static Tool tool1;
	Tool tool2;
	Coast c1;
	Coast c2;
	Coast c3;
	Barriers b8;
	Barriers b9;
	Barriers b10;
	ArrayList<Coast> coast;
	ArrayList<Barriers> barriers2;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		b2 = new Barriers(300,300,eBarrierType.Gabion);
		b3 = new Barriers(400,300, eBarrierType.EMPTY);
		b4 = new Barriers(500,300, eBarrierType.Wall);
		b5 = new Barriers(600,300, eBarrierType.Wall);
		b6 = new Barriers(600,300, eBarrierType.Gabion);
		b7 = new Barriers(600,300, eBarrierType.EMPTY);
		
		barriers = new ArrayList<Barriers>();
		barriers.add(b2);
		barriers.add(b3);
		barriers.add(b4);
		barriers.add(b5);
		barriers.add(b6);
		barriers.add(b7);
		
		tool1 = new Tool(barriers);
		
	}

	@Test
	public void nextBarrierTest() {
		assertEquals(barriers.get(0).getType(),eBarrierType.Gabion);
		tool1.nextBarrier();
		assertEquals(barriers.get(0).getType(),eBarrierType.EMPTY);
		tool1.addTime(1000);
		assertFalse(tool1.doneBuilding());
		int i = 0;
		while(i!=4){
			tool1.nextBarrier();
			i++;
		}
		
		assertTrue(tool1.doneBuilding());
	}
	
	@Test
	public void stopErosionTest(){
		b8 = new Barriers(700,300, eBarrierType.EMPTY);
		b9 = new Barriers(800,300, eBarrierType.EMPTY);
		b10 = new Barriers(900,300, eBarrierType.EMPTY);
		barriers2 = new ArrayList<Barriers>();
		barriers2.add(b8);
		barriers2.add(b9);
		barriers2.add(b10);
		tool2 = new Tool(barriers2);
		c1 = new Coast(700,300, b8);
		c2 = new Coast(800,300, b9);
		c3 = new Coast(900, 300, b10);
		coast = new ArrayList<Coast>();
		coast.add(c1);
		coast.add(c2);
		coast.add(c3);
		
		assertFalse(c1.isProtected());
		assertFalse(c2.isProtected());
		assertFalse(c3.isProtected());
		tool2.stopErosion(coast);
		assertTrue(c1.isProtected());
		assertTrue(c2.isProtected());
		assertTrue(c3.isProtected());
		
		
		
	}

}
