
package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eDebrisType;
import model.Bin;
import view.EstuaryGame;

public class BinTest {

	public static Bin trashBin;
	public static Bin recycleBin;
	public static GameController gc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trashBin = new Bin(eDebrisType.TRASH);
		recycleBin = new Bin(eDebrisType.RECYCLING);
		gc = new GameController(new EstuaryGame());
	}

	@Test
	public void binTest() {
		assertNotNull(trashBin);
		assertNotNull(recycleBin);
		assertNotNull(gc.getItems().getRecycleBin());
		assertNotNull(gc.getItems().getTrashBin());
		assertEquals(trashBin.getHeight(), 50);
		assertEquals(trashBin.getWidth(), 50);
		assertEquals(recycleBin.getHeight(), 50);
		assertEquals(recycleBin.getWidth(), 50);
	}
	@Test
	public void binPositionTest(){
		assertEquals(gc.getItems().getRecycleBin().getPosX(), 700);
		assertEquals(gc.getItems().getRecycleBin().getPosY(), 150);
		assertEquals(gc.getItems().getTrashBin().getPosX(), 50);
		assertEquals(gc.getItems().getTrashBin().getPosY(), 150);
	}

	@Test
	public void typeTest(){
		assertTrue(trashBin.getDebrisType() == eDebrisType.TRASH);
		assertTrue(recycleBin.getDebrisType() == eDebrisType.RECYCLING);
		assertTrue(gc.getItems().getRecycleBin().getDebrisType() == eDebrisType.RECYCLING);
		assertTrue(gc.getItems().getTrashBin().getDebrisType() == eDebrisType.TRASH);
	}
}
