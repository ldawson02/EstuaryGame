package test.model;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eBarrierType;
import eNums.eDebrisType;
import model.Barriers;
import model.Debris;
import model.Gabions;
import model.Player;
import model.Wall;

public class BarriersTest {
	public static Player p;
	public static ArrayList<Barriers> barriers;
	public static GameController gc;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = new Player();
		Barriers b1 = new Barriers(0,0);
		b1.setType(eBarrierType.Gabion);
		
		barriers.add(b1);
		/**gabions.add(new Gabions(0,5));
		gabions.add(new Gabions(10,10));
		gabions.add(new Gabions(30,10));
		
		walls.add(new Wall(0,0));
		walls.add(new Wall(9,13));**/
		
		
	}
	
	@Test
	public void erodeTest(){
		//b1.setbTimer();
		
	}
	
	@Test
	public void setupCoastTest(){
		
	}
	
	@Test
	public void setBarrierTypeTest(){
		
	}

}
