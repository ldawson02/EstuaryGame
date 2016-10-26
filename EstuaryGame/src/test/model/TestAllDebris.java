package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisType;
import model.AllDebris;
import model.Debris;

public class TestAllDebris {

	public AllDebris ad;
	public Debris deb1;
	public Debris deb2;
	public Debris deb3;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		ad = new AllDebris();
		//TODO: these should probably not be empty constructors
		deb1 = new Debris(eDebrisType.RECYCLING);
		deb2 = new Debris(eDebrisType.TRASH);
		deb3 = new Debris(eDebrisType.RECYCLING);
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	
	}

	@Before
	public void setUp() throws Exception {
		ad.addItem(deb1);
	}

	@After
	public void tearDown() throws Exception {
		ad.clearAll();
	}

	@Test
	public void testFind() {
		//Find deb1, don't find deb2
		assertEquals(ad.find(deb1), true);
		assertEquals(ad.find(deb2), false);
	}
	
	@Test
	public void testAddItem() {
		//deb2 is not there
		assertEquals(ad.find(deb2), false);
		//deb2 is there
		ad.addItem(deb2);
		assertEquals(ad.find(deb2), true);
	}
	
	@Test
	public void testRemoveItem() {
		//deb3 is not there
		assertEquals(ad.find(deb3), false);
		//deb3 is there, deb1 is there
		ad.addItem(deb3);
		assertEquals(ad.find(deb3), true);
		assertEquals(ad.find(deb1), true);
		//deb3 removed
		assertEquals(ad.removeItem(deb3), deb3);
		assertEquals(ad.find(deb3), false);
		//deb1 removed
		ad.removeItem(deb1);
		assertEquals(ad.removeItem(deb1), deb1);
		assertEquals(ad.find(deb1), false);
	}
	
	@Test
	public void testCount() {
		//Should have one item
		assertEquals(ad.count(), 1);
		//Add one
		ad.addItem(deb2);
		assertEquals(ad.count(), 2);
		//Remove one
		ad.removeItem(deb2);
		assertEquals(ad.count(), 1);
		//Clear out
		ad.clearAll();
		assertEquals(ad.count(), 0);
	}
	
	@Test
	public void testClearAll() {
		ad.addItem(deb2);
		ad.addItem(deb3);
		assertEquals(ad.count(), 3);
		//remove all
		ad.clearAll();
		assertEquals(ad.count(), 0);
	}

}
