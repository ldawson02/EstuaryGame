package model;
/**
 * author: rachel muzzelo
 * @since 10/25/16
 * Barrier interface. Each barrier has build, decay, and crumble methods.
 */
public abstract class Barriers extends Item{
	int decayTime;
	int health;
	
	/**
	 * Constructor for Barrier
	 */
	public Barriers(int x, int y){
		super(x,y);
	};
	
	public abstract void build();
	public abstract void decay();
	public abstract void crumble();

}
