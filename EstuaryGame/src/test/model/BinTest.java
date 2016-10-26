package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisType;
import model.Bin;

public class BinTest {

	public Bin trashBin;
	public Bin recycleBin;
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		trashBin = new Bin(eDebrisType.TRASH);
		recycleBin = new Bin(eDebrisType.RECYCLING);
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void buildTest() {
		assertNotNull(trashBin);
		assertNotNull(recycleBin);
	}
	
	@Test
	public void typeTest(){
		assertTrue(trashBin.getDebrisType() == eDebrisType.TRASH);
		assertTrue(recycleBin.getDebrisType() == eDebrisType.RECYCLING);
	}
}
