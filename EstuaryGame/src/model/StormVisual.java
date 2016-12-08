package model;

public class StormVisual extends Item {

	private int speed;
	
	public static final int defaultHeight = 200;
	public static final int defaultWidth = 250;
	
	public StormVisual(int x, int y) {
		this.updatePos(x, y);
	}
	
	public void move() {
		updatePos(this.getPosX() + speed, this.getPosY() + speed);
	}
	
}
