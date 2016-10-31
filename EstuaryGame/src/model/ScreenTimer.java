package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eNums.eScreenTimerState;

/**
 * This is the class that handles the timer model on screen. This
 * is NOT the actual game timer that handles the tick, just the
 * visual timer.
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 10/25/16
 */

public class ScreenTimer extends Item {

	public int timeRemaining;
	public eScreenTimerState state;

	
	public ScreenTimer() {
		state = eScreenTimerState.OFF;
	}
	
	/**
	 * @return the timeRemaining
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * @param timeRemaining the timeRemaining to set
	 */
	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	/**
	 * @return the state
	 */
	public eScreenTimerState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(eScreenTimerState state) {
		this.state = state;
	}

	public void start(){
		this.state = eScreenTimerState.ON;
	}
	public void freeze(){
		
	}
	
	public void Continue(){
		
	}
	
	public void endGame(){
		
	}
	
}
