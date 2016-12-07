package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import controller.ActiveItems;
import controller.GameController;
import controller.GameController.spawnDebris;
import eNums.eBarrierType;
import model.Barriers;
import model.Storm;
import view.EstuaryGame;

public class StormTest {

	@Test
	public void getAppearedTest() {
		assertFalse(Storm.getAppeared());  //starts off false
		Storm.setAppeared(true);
		assertTrue(Storm.getAppeared());
	}
	
	@Test
	public void setAppearedTest() {
		assertFalse(Storm.getAppeared());
		Storm.setAppeared(true);
		assertTrue(Storm.getAppeared());
		Storm.setAppeared(false);
		assertFalse(Storm.getAppeared());
	}
	
	@Test
	public void stormEffectsTest() {
		destroyBarriersTest();
		addDebrisTest();
	}
	
	@Test
	public void destroyBarriersTest() {
		ActiveItems ai = new ActiveItems();
		ai.addBarrier(new Barriers(1, 2, eBarrierType.Gabion));
		ai.addBarrier(new Barriers(2, 2, eBarrierType.Gabion));
		ai.addBarrier(new Barriers(3, 2, eBarrierType.Wall));
		ai.addBarrier(new Barriers(4, 2, eBarrierType.EMPTY));
		ai.addBarrier(new Barriers(5, 2, eBarrierType.Gabion));
		ai.addBarrier(new Barriers(1, 2, eBarrierType.EMPTY));
		//has 6 total barriers, 4 that are active, should destroy half of that -> 2 active leftover
		Storm.destroyBarriers(ai);
		assertEquals(ai.numActiveBarriers(), 2);
		ai.addBarrier(new Barriers(1, 1, eBarrierType.Wall));
		ai.addBarrier(new Barriers(1, 1, eBarrierType.Gabion));
		ai.addBarrier(new Barriers(1, 1, eBarrierType.Wall));
		//now 9 total, 5 that are active -> should have 3 active leftover
		Storm.destroyBarriers(ai);
		assertEquals(ai.numActiveBarriers(), 3);
	}
	
	@Test
	public void addDebrisTest() {
		/*
		int addDebris = (int) (Math.random() * 6 + 5); 
		for (int i = 0; i < addDebris; i++) {
			ai.addDebris(sd.newDebris());
			System.out.println("storm debris created");
		}
		 */
		//should add 5-10 pieces of debris
		EstuaryGame eg = new EstuaryGame();
		GameController gc = new GameController(eg);
		gc.startGame();
		int currDebris = gc.getItems().getAllDebris().size();
		assertEquals(currDebris, 2);
		//current # debris = 2, after storm should be 7-12
		Storm.addDebris(gc.getItems(), gc.getSpawnDebris());
		int afterStorm = gc.getItems().getAllDebris().size();
		assertTrue((afterStorm >= 7) && (afterStorm <= 12));
	}
}
