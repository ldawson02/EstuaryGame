package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the class that handles the timer model on screen. This
 * is NOT the actual game timer that handles the tick, just the
 * visual timer.
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 10/25/16
 */

public class ScreenTimer extends Item implements ActionListener{

	public int timeRemaining;
	public String state;

	
	public ScreenTimer() {
		state = "off";
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
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public void actionPerformed(ActionEvent e){}
	
	public void freeze(){
		
	}
	
	public void Continue(){
		
	}
	
	public void endGame(){
		
	}
	
}
