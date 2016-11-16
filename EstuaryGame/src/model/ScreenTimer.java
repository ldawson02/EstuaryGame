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

	final int MAX_TIME = 10000; //ms
	
	private int maxTime;
	private int timeRemaining;
	private int elapsedTime;
    private double size = 50;
    private double doublePosX = 375;
    private double doublePosY = 10;
	
    private eScreenTimerState state;
	
	public ScreenTimer() {
		state = eScreenTimerState.OFF;
		maxTime = MAX_TIME;
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
	 * @return the maxTime
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the elapsedTime
	 */
	public int getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
		updateRemaining();
	}

	/**
	 * @return the size
	 */
	public double getSize() {
		return size;
	}

	/**
	 * @return the doublePosX
	 */
	public double getDoublePosX() {
		return doublePosX;
	}

	/**
	 * @param doublePosX the doublePosX to set
	 */
	public void setDoublePosX(double doublePosX) {
		this.doublePosX = doublePosX;
	}

	/**
	 * @return the doublePosY
	 */
	public double getDoublePosY() {
		return doublePosY;
	}

	/**
	 * @param doublePosY the doublePosY to set
	 */
	public void setDoublePosY(double doublePosY) {
		this.doublePosY = doublePosY;
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
	
	public void updateRemaining() {
		timeRemaining = maxTime - elapsedTime;
		if (timeRemaining <= 0) {
			endGame();
			System.out.println("Game over!");
		}
	}
	
	public void freeze(){
		setState(eScreenTimerState.FROZEN);
	}
	
	public void Continue(){
		setState(eScreenTimerState.ON);
	}
	
	public void endGame(){
		setState(eScreenTimerState.OFF);
	}
	
}
