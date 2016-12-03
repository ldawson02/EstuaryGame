package model;

import java.util.ArrayList;

import controller.GameController;
import eNums.eBarrierType;
import eNums.eDebrisType;

public class Storm {
	
	/**
	* Storm class- has appear(), destroyBarriers(), and addDebris() methods
	* just once
	* 
	* @author Esme Li
	* @version 1.0
	* @since 10/25/16
	*/
		
	/**
	 * Constructs a Storm at 0,0
	 */
	
	/**
	 * Makes the storm in the game at x, y 
	 */
	public void appear() {
		
	}
	
	/**
	 * Destroys the barriers 
	 */
	public void destroyBarriers(GameController gc) {
		//50%
		int destroyBarriers = gc.getItems().getAllBarriers().size() / 2;
		
		while (destroyBarriers > 0) {
			ArrayList<Barriers> gcBarriers = gc.getItems().getAllBarriers();
			int random = (int) (Math.random() * gcBarriers.size());
			if (gcBarriers.get(random).getType() != eBarrierType.EMPTY) {
				gc.getItems().getAllBarriers().remove(random);
				destroyBarriers--;
			}
		}
	}
	
	public void stormEffects(GameController gc) {
	}
	
	/**
	 * Adds a # of trash, numTrash
	 */
	public void addDebris(GameController gc) {
		//total+5-10
		int addDebris = (int) (Math.random() * 6 + 5); 
		for (int i = 0; i < addDebris; i++) {
			
		}
		System.out.print(addDebris);
	}
}
