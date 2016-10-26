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
	public HealthBar(){
	};
	
	public HealthBar(int x, int y){
		super(x,y);
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
	
	public void sethealth(int health){
		this.health = health;
	}

}
