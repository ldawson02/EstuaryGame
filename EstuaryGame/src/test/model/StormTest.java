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
		EstuaryGame eg = new EstuaryGame();
		GameController gc = new GameController(eg);
		gc.startGame();
		gc.getItems().addBarrier(new Barriers(1, 2, eBarrierType.Gabion));
		gc.getItems().addBarrier(new Barriers(2, 2, eBarrierType.Gabion));
		gc.getItems().addBarrier(new Barriers(3, 2, eBarrierType.Wall));
		gc.getItems().addBarrier(new Barriers(4, 2, eBarrierType.EMPTY));
		gc.getItems().addBarrier(new Barriers(5, 2, eBarrierType.Gabion));
		gc.getItems().addBarrier(new Barriers(1, 2, eBarrierType.EMPTY));

		int currDebris = gc.getItems().getAllDebris().size();
		assertEquals(currDebris, 2);
		
		Storm.stormEffects(gc.getItems(), gc.getSpawnDebris());
		//has 6 total barriers, 4 that are active, should destroy half of that -> 2 active leftover
		assertEquals(gc.getItems().numActiveBarriers(), 2);
		int afterStorm = gc.getItems().getAllDebris().size();
		//current # debris = 2, after storm should be 7-12
		assertTrue((afterStorm >= 7) && (afterStorm <= 12));
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
