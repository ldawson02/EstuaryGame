package model;

import java.util.ArrayList;

import controller.GameController;
import eNums.eFloaterState;
import eNums.eHelperState;

public class Helper extends Item {
	private eHelperState state = eHelperState.WALKING;
	private Remove removeInstance;
	private Debris toLift;
	private int finalY;
	private int timeInStage = 0;
	
	public static final int defaultWidth = 67;
	public static final int defaultHeight = 80;
	
	public Helper(Remove r){
		super(740,0);
		this.setHeight(defaultWidth);
		this.setWidth(defaultHeight);
		removeInstance = r;
	}
	
	public void setFinalY(ArrayList<Debris> debris){
		int point = 600;
		for(Debris d: debris){
			if(d.getState() == eFloaterState.RESTING) {
				if(d.getPosY() < point){
					point = d.getPosY();
					toLift = d;
					System.out.println("new minY = " + d.getPosY());
				}
			}
		}
		
		this.finalY = point;
	}
	
	public Remove getPower(){
		return removeInstance;
	}
	
	public int getTimeInStage(){
		return timeInStage;
	}
	
	public void addTime(int delta){
		timeInStage+=delta;
		checkState();
	}
	
	public void nextState(){
		state = state.nextState();
		timeInStage = 0;
	}
	
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
	
	public void walkCheck(){
		System.out.println("walk checking, finalY = " + getFinalY());
		if(this.getPosY()>this.getFinalY()){
			nextState();
		}
	}
	
	public void delayCheck(){
		if(timeInStage >= state.getTimeLimit()){
			nextState();
		}
	}
	
	public void checkOffScreen(){
		if(this.getPosX()> GameController.dimX){
			nextState();
		}
	}
	
	public int getFinalY(){
		return finalY;
	}
	
	public eHelperState getState(){
		return state;
	}
	
	public void setState(eHelperState s){
		state = s;
		timeInStage = 0;
	}
	
}
