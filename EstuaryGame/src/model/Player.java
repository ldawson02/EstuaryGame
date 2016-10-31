package model;

public class Player extends Item implements Movers {

	public int speed;
	public int direction;
	
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}
		
	public void buildGabion(){};
	public void buildWall(){};
	public void pickUpDebris(){};
	public void throwDebris(Debris d){};
}
