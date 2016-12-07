package model;

import java.io.Serializable;

import eNums.ePlayerState;

public class Player extends Item implements Serializable{

	public int speed = 10;
	public ePlayerState state;
	public final int idleHeight = 60;
	public final int width = 120;
	public final int liftingHeight = 72;
	/**
	 * construct a player in specific height, width and state.
	 */
	public Player(){
		super();
		super.setHeight(idleHeight);
		super.setWidth(width);
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
			this.setHeight(idleHeight);
			this.setWidth(width);
		}
		else if (state == ePlayerState.Lifting) {
			this.setHeight(liftingHeight);
			this.setWidth(width);
		}
		
	}


}
