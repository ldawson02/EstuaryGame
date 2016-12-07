package test.view;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import eNums.eAnimation;
import model.ScreenTimer;
import view.ImageLibrary;
import view.ImageSequence;

public class ImageLibraryTest {
	
	static ImageLibrary lib;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lib = ImageLibrary.loadLibrary();
	}
	
	@Test
	public void testLoad() {
		eAnimation[] allAnims = eAnimation.values();
		assertEquals(lib.getLibrary().size(), allAnims.length);
		for (eAnimation e: allAnims) {
			assertEquals(lib.getLibrary().containsKey(e), true);
		}
		
		assertEquals(lib.getCoastLibrary().length, 4);
	}

	@Test
	public void testDraw() {
		//Can only test the single frames
		ImageSequence seq = lib.getLibrary().get(eAnimation.background);
		assertEquals(lib.draw(eAnimation.background), seq.draw());
		ImageSequence seq2 = lib.getLibrary().get(eAnimation.playerLift);
		assertEquals(lib.draw(eAnimation.playerLift), seq2.draw());
	}
	
}
