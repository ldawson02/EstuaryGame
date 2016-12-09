package model;

import java.util.ArrayList;

import controller.GameController;
import eNums.eFloaterState;
import eNums.eHelperState;
/**
 * the halper class has a helper state,remove instancema time in stage 
 * @author megan
 *
 */
public class Helper extends Item {
	private eHelperState state = eHelperState.WALKING;
	private Remove removeInstance;
	private Debris toLift;
	private int finalY;
	private int timeInStage = 0;
	private boolean right = true;
	
	public static final int defaultWidth = 67;
	public static final int defaultHeight = 80;
	/**
	 * construct a helper
	 */
	public Helper(){
		super(740,0);
		this.setHeight(defaultWidth);
		this.setWidth(defaultHeight);
	}
	/**
	 * to see if it is right
	 * @return
	 */
	public boolean isRight(){
		return right;
	}
	/**
	 * set the final y location
	 * @param debris
	 */
	public void setFinalY(ArrayList<Debris> debris){
		int point = 600;
		for(Debris d: debris){
			if(d.getState() == eFloaterState.RESTING) {
				if(d.getPosY() < point){
					point = d.getPosY();
					toLift = d;
				}
			}
		}
		if(toLift.getPosX()<=GameController.dimX/2){
			right = false;
			this.updatePosX(0);
		}
		
		System.out.println("debris found with finalY of: " + point);
		this.finalY = point;
	}
	/**
	 * get the power
	 * @return removeinstance
	 */
	public Remove getPower(){
		return removeInstance;
	}
	/**
	 * get the time in stage
	 * @return timeinstage
	 */
	public int getTimeInStage(){
		return timeInStage;
	}
	/**
	 * add time to stage
	 * @param delta
	 */
	public void addTime(int delta){
		timeInStage+=delta;
		checkState();
	}
	/**
	 * the helper's next state 
	 */
	public void nextState(){
		state = state.nextState();
		timeInStage = 0;
	}
	/**
	 * illustrates the helper's state and check it
	 */
	public void checkState(){
		switch(state){
		case WALKING:
			walkCheck();
			break;
		case PICKING_UP:
		case HOLDING:
			delayCheck();
			break;
		case WALKING_OFF:
		default:
			checkOffScreen();
		}
	}
	/**
	 * check the walk the postion y is greater than the final position, then go to the next state
	 */
	public void walkCheck(){
		if(this.getPosY()>this.getFinalY()){
			nextState();
		}
	}
	/**
	 * check the delay, if time in stage is greater than limit, then go to next state
	 */
	public void delayCheck(){
		if(timeInStage >= state.getTimeLimit()){
			nextState();
		}
	}
	/**
	 * this position is out of the screen,  then go to next state
	 */
	
	public void checkOffScreen(){
		if(this.getPosX()> GameController.dimX || this.getPosX()<(-1*this.getWidth())){
			nextState();
		}
	}
	/**
	 * get the final y postion
	 * @return finalY
	 */
	public int getFinalY(){
		return finalY;
	}
	/**
	 * get the helper state
	 * @return state
	 */
	public eHelperState getState(){
		return state;
	}
	
	/**
	 * set the helper state
	 * @param s
	 */
	public void setState(eHelperState s){
		state = s;
		timeInStage = 0;
	}
	
	public void setTimeInStage(int time){
		this.timeInStage = time;
	}
	
}
