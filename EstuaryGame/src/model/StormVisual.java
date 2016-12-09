package model;

public class StormVisual extends Item {
/**
 * the storm's x speed and y speed are below
 */
	private int speedx = 3;
	private int speedy = 2;
	
	public static final int defaultHeight = 250;
	public static final int defaultWidth = 250;
	
	/**
	 * set up the update postion for storm
	 * @param x
	 * @param y
	 */
	public StormVisual(int x, int y) {
		this.updatePos(-250, 600);
	}
	/**
	 * set up how storm moves, get the position and add or subtract the speed position
	 */
	public void move() {
		updatePos(this.getPosX() + speedx, this.getPosY() - speedy);
	}
	
}
