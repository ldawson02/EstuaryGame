package model;

/**
 * 
 * 
 * @author Rachel Muzzelo
 * @version 1.0
 * @since 10/26/16
 */
public abstract class Floater extends Item {
	private int speed;
	private int vertex;
	public Floater(){
		super();
	}
	public Floater(int x, int y){
		super(x,y);
	};
	public abstract void floating();
	public abstract void catching();
	
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getVertex() {
		return vertex;
	}
}
