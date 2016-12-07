package model;

import eNums.ePlayerState;

public class Player extends Item{

	public int speed = 10;
	public ePlayerState state;
	public static final int idleHeight = 60;
	public static final int defaultWidth = 120;
	public static final int liftingHeight = 72;
	
	/**
	 * construct a player in specific height, width and state.
	 */
	public Player(){
		super();
		super.setHeight(idleHeight);
		super.setWidth(defaultWidth);
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
			this.setWidth(defaultWidth);
		}
		else if (state == ePlayerState.Lifting) {
			this.setHeight(liftingHeight);
			this.setWidth(defaultWidth);
		}
		
	}


}
