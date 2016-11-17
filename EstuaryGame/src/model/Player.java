package model;

import eNums.ePlayerState;

public class Player extends Item implements Movers {

	public int speed = 10;
	public ePlayerState state;
	
	public Player(){
		this.setHeight(119);
		this.setWidth(243);
		this.setState(ePlayerState.Idle);
	}
	
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

	/**
	 * @return the state
	 */
	public ePlayerState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ePlayerState state) {
		this.state = state;
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
