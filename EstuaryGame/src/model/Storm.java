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
	* Storm class- has appear(), destroyBarriers(), and addDebris() methods
	* just once
	* 
	* @author Esme Li
	* @version 1.0
	* @since 10/25/16
	*/
	
	private static boolean appeared = false;
	
	public static boolean getAppeared() {
		return appeared;
	}
	
	public static void setAppeared(boolean b) {
		appeared = b;
	}
	
	/**
	 * Makes the storm in the game at x, y 
	 */
	
	public static void stormEffects(ActiveItems ai, spawnDebris sd) {
		System.out.println("storm commence");
		destroyBarriers(ai);
		System.out.println("done barriers destroyed");
		addDebris(ai, sd);
		System.out.println("done debris added");
	}
	
	/**
	 * Destroys the half of the existing barriers
	 */
	public static void destroyBarriers(ActiveItems ai) {
		
		int activeBarriers = ai.numActiveBarriers();
		System.out.println("# active barriers: " + activeBarriers);
		int destroyBarriers = activeBarriers/2;
		//# of barriers to be destroyed
		
		//while (!ai.allEmptyBarriers()) {
			while (destroyBarriers > 0) {
				ArrayList<Barriers> barriers = ai.getAllBarriers();
				int random = (int) (Math.random() * barriers.size());
				if (barriers.get(random).getType() != eBarrierType.EMPTY) {
					barriers.get(random).setType(eBarrierType.EMPTY);
					destroyBarriers--;
					System.out.println("storm destroyed a barrier");
				}
			}
		
	}
	
	/**
	 * Adds 5-10 debris to the coast
	 */
	public static void addDebris(ActiveItems ai, spawnDebris sd) {
		
		int addDebris = (int) (Math.random() * 6 + 5); 
		for (int i = 0; i < addDebris; i++) {
			ai.addDebris(sd.newDebris());
			System.out.println("storm debris created");
		}
		System.out.print(addDebris);
	}
}
