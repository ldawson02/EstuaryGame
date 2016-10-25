package model;
	
/**
* The HealthBar class holds information about the health of the estuary and has the update method
* 
* @author Esme Li
* @version 1.0
* @since 10/25/16
*/

public class HealthBar extends Item {
	private int maxHealth;
	private int health;
	
	/**
	 * Constructs a HealthBar with 
	 * @param max
	 * @param health
	 */
	
	public HealthBar(int max, int current){
		super(max, current);
	};
	
	/**
	 * Updates the current health of the estuary
	 * @param points
	 */
	public void update(int points) {
		
	};
	
	public int gethealth(){
		return health;
	}

}
