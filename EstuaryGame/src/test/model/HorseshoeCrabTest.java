package test.model;

import static org.junit.Assert.*; 
import org.junit.AfterClass;
import org.junit.BeforeClass;

import model.HorseshoeCrab;

public class HorseshoeCrabTest {
	public HorseshoeCrab hsc;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception{
		hsc = new HorseshoeCrab(100,200);
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception{
		}
	
	public void appearTest(){
		hsc.appear();
		assertEquals(hsc.getXloc(),100);
		assertEquals(hsc.getYloc(),200);
		assertNotEquals(hsc.getXloc(),150);
		assertNotEquals(hsc.getYloc(),210);
	}
	
	public void disppearTest(){
		hsc.disappear();
		assertNull(hsc);
	}
}
