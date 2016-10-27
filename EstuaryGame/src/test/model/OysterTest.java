package test.model;

import static org.junit.Assert.*; 

import org.junit.AfterClass;
import org.junit.BeforeClass;
import model.Oyster;

public class OysterTest {
	public Oyster o1;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception{
		o1=new Oyster(50,200);
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception{
		}
	
	public void appearTest(){
		o1.appear();
		assertEquals(o1.getXloc(),50);
		assertEquals(o1.getYloc(),200);
		assertNotEquals(o1.getXloc(),150);
		assertNotEquals(o1.getYloc(),210);
	}
	
	public void disppearTest(){
		o1.disappear();
		assertNull(o1);
	}
}