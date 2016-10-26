package model;
	
/**
* The HealthBar class holds information about the health of the estuary and has the update method
* 
* @author Esme Li
* @version 1.0
* @since 10/25/16
*/

public class HealthBar extends Item {
	private static int maxHealth = 100;
	private int health;
	
	/**
	 * Constructs a HealthBar with 
	 * @param max
	 * @param health
	 */
	public HealthBar(){
		super();
	};
	
	/**
	 * Updates the current health of the estuary
	 * @param points
	 */
	public void update(int points) {
		this.health += points;
	};
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}

}
