package model;
/**
 * Barrier interface. Each barrier has build, decay, and crumble methods.
 * 
 * @author: Rachel Muzzelo
 * @version 1.0
 * @since 10/25/16
 */
public abstract class Barriers extends Item implements Interactable{
	//rate at which barrier decays
	private int decayTime;
	//overall state of the barrier
	private int health;
	
	/**
	 * Constructor for Barrier
	 */
	public Barriers(int x, int y){
		super(x,y);
	};
	
	public abstract void build();
	public abstract void decay(int time);
	public abstract void crumble();

	public int getDecayTime() {
		return decayTime;
	}

	public void setDecayTime(int decayTime) {
		this.decayTime = decayTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	

}
