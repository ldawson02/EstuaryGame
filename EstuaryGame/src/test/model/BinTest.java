
package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eDebrisType;
import model.Bin;

public class BinTest {

	public static Bin trashBin;
	public static Bin recycleBin;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trashBin = new Bin(eDebrisType.TRASH);
		recycleBin = new Bin(eDebrisType.RECYCLING);
	}

	@Test
	public void test() {
		assertNotNull(trashBin);
		assertNotNull(recycleBin);
		assertEquals(trashBin.getHeight(), 50);
		assertEquals(trashBin.getWidth(), 50);
		assertEquals(recycleBin.getHeight(), 50);
		assertEquals(recycleBin.getWidth(), 50);
	}

	@Test
	public void typeTest(){
		assertTrue(trashBin.getDebrisType() == eDebrisType.TRASH);
		assertTrue(recycleBin.getDebrisType() == eDebrisType.RECYCLING);
	}
}
