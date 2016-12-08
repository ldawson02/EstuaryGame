package model;

import java.io.Serializable;
import java.util.ArrayList;

import controller.ActiveItems;
import controller.GameController;
import controller.GameController.spawnDebris;
import eNums.eBarrierType;
import eNums.eDebrisType;

public class Storm implements Serializable {
	
	/**
	* Storm class causes effects to happen in the game, it can destroy barriers and add debris.
	* Also contains information about its appearance in the game.
	* 
	* @author Esme Li
	* @version 1.0
	* @since 10/25/16
	*/
	
	private static boolean appeared = false;
	
	/**
	 * getter for whether the storm has appeared yet
	 * @param appeared
	 */
	public static boolean getAppeared() {
		return appeared;
	}
	
	/**
	 * sets whether the storm has appeared
	 */
	public static void setAppeared(boolean b) {
		appeared = b;
	}
	
	/**
	 * combines the effects of destroying barriers and adding debris 
	 */
	public static void stormEffects(ActiveItems ai, spawnDebris sd) {
		System.out.println("storm commence");
		destroyBarriers(ai);
		//System.out.println("done barriers destroyed");
		addDebris(ai, sd);
		//System.out.println("done debris added");
	}
	
	/**
	 * Destroys the half of the existing barriers
	 * @param the active items and barriers to destroy
	 */
	public static void destroyBarriers(ActiveItems ai) {
		
		int activeBarriers = ai.numActiveBarriers();
		//System.out.println("# active barriers: " + activeBarriers);
		int destroyBarriers = activeBarriers/2;
		//# of barriers to be destroyed
		
		while (destroyBarriers > 0) {
			ArrayList<Barriers> barriers = ai.getAllBarriers();
			int random = (int) (Math.random() * barriers.size());
			if (barriers.get(random).getType() != eBarrierType.EMPTY) {
				barriers.get(random).setType(eBarrierType.EMPTY);
				destroyBarriers--;
				//System.out.println("storm destroyed a barrier");
			}
		}
		
	}
	
	/**
	 * Adds 5-10 debris to the coast
	 * @param the active items and debris to add to, the debris creater
	 */
	public static void addDebris(ActiveItems ai, spawnDebris sd) {
		
		int addDebris = (int) (Math.random() * 6 + 5); 
		for (int i = 0; i < addDebris; i++) {
			ai.addDebris(sd.newDebris());
			//System.out.println("storm debris created");
		}
		//System.out.print(addDebris);
	}
}
