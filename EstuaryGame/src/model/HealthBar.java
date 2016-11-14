package model;
	
/**
* The HealthBar class holds information about the health of the estuary and has the update method
* 
* @author Esme Li
* @version 1.0
* @since 10/25/16
*/

public class HealthBar extends Item {
	private final int maxHealth = 100;
	private final int minHealth = 0;
	private final int height = 100;
	private final int width = 30;
	private int health;
	
	/**
	 * Constructs a HealthBar with 
	 * @param max
	 * @param health
	 */
	public HealthBar(){
		super();
		health = 80;
	};
	
	/**
	 * Updates the current health of the estuary
	 * @param points
	 */
	public void update(int points) {
		this.health += points;
		if (health > maxHealth) {
			health = 100;
		}
		else if (health < minHealth) {
			health = 0;
		}
	};
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}

	/**
	 * @return the maxHealth
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return the minHealth
	 */
	public int getMinHealth() {
		return minHealth;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

}
