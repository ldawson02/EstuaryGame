package model;

public class StormVisual extends Item {

	private int speedx = 3;
	private int speedy = 2;
	
	public static final int defaultHeight = 250;
	public static final int defaultWidth = 250;
	
	public StormVisual() {
		this.updatePos(-250, 600);
	}
	
	public void move() {
		updatePos(this.getPosX() + speedx, this.getPosY() - speedy);
	}
	
	public int getSpeedX(){
		return speedx;
	}
	
	public int getSpeedY(){
		return speedy;
	}
}
