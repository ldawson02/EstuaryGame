package model;

import eNums.ePlayerState;

public class Player extends Item implements Movers {

	public int speed = 10;
	public ePlayerState state;
	
	/**
	 * construct a player in specific height, width and state.
	 */
	public Player(){
		this.setHeight(60);
		this.setWidth(120);
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
	 * affects the visuals wildly
	 */
	public void setState(ePlayerState state) {
		this.state = state;
		
		if (state == ePlayerState.Idle) {
			this.setHeight(60);
			this.setWidth(120);
		}
		else if (state == ePlayerState.Lifting) {
			this.setHeight(72);
			this.setWidth(120);
		}
		
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
