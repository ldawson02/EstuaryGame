package model;
/**
 * Barrier interface. Each barrier has build, decay, and crumble methods.
 * 
 * @author: Rachel Muzzelo
 * @version 1.0
 * @since 10/25/16
 */
public abstract class Barriers extends Item{
	int decayTime;
	int health;
	
	/**
	 * Constructor for Barrier
	 */
	public Barriers(){
		
	};
	
	public abstract void build();
	public abstract void decay();
	public abstract void crumble();

}
