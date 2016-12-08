package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

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

public class ScreenTimer extends Item implements Serializable {

	final int MAX_TIME = 120000; //ms
	
	private int maxTime;
	private int timeRemaining;
	private int elapsedTime;
    private double size = 50;
    private double doublePosX = 375;
    private double doublePosY = 10;
	
    private eScreenTimerState state;
	
	public ScreenTimer() {
		super(400, 20);
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

	/**
	 * sets the timer state to ON when starting the game
	 */
	public void start(){
		this.state = eScreenTimerState.ON;
	}
	
	/**
	 * update the time when the game is about to be over
	 */
	public void updateRemaining() {
		timeRemaining = maxTime - elapsedTime;
		if (timeRemaining <= 0) {
			endGame();
			System.out.println("Game over!");
		}
	}
	
	/**
	 * sets timer state to FROZEN
	 */
	public void freeze(){
		setState(eScreenTimerState.FROZEN);
	}
	
	/**
	 * sets the timer state to ON (for after it has been FROZEN)
	 */
	public void Continue(){
		setState(eScreenTimerState.ON);
	}
	
	/**
	 * sets the timer state to OFF 
	 */
	public void endGame(){
		setState(eScreenTimerState.OFF);
	}
	
}
