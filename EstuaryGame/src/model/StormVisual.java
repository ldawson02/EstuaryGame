package model;

public class StormVisual extends Item {

	private int speedx = 3;
	private int speedy = 2;
	
	public static final int defaultHeight = 250;
	public static final int defaultWidth = 250;
	
	public StormVisual(int x, int y) {
		this.updatePos(-250, 600);
	}
	
	public void move() {
		updatePos(this.getPosX() + speedx, this.getPosY() - speedy);
	}
	
}