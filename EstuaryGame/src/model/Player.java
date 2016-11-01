package model;

public class Player extends Item implements Movers {

	public int speed = 10;
	public int direction;
	
	public Player(){
		this.setHeight(40);
		this.setWidth(40);
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}
		
	public void buildGabion(){};
	public void buildWall(){};
	public void pickUpDebris(){};
	public void throwDebris(Debris d){};
}
