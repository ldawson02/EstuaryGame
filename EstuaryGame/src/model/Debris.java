package model;

import eNums.eFloaterState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import controller.GameController;
import eNums.eDebrisType;
import eNums.eThrowDirection;

/**
 * 
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 10/25/16
 */

public class Debris extends Floater implements Serializable {

	private eDebrisType type;
	private eFloaterState state;
	private boolean correctBin;
	private eThrowDirection throwDir = eThrowDirection.LEFT;
	ArrayList<Bin> LR;
	
	/**
	 * Private no-arg constructor to prevent creating a debris item without
	 * a type (trash vs recycling)
	 */
	private Debris() {
		super();
	}
	public Debris(int x, int y){
		super(x,y);
		
	};
	public Debris(eDebrisType etype) {
		super();
		this.type = etype;
		this.state = eFloaterState.MOVING; // Moving by default on creation
		LR = new ArrayList<Bin>();
	}
	
	/**
	 * getter for type
	 * @return the type
	 */
	public eDebrisType getType() {
		return type;
	}

	/**
	 * set the type
	 * @param type the type to set
	 */
	public void setType(eDebrisType type) {
		this.type = type;
	}

	/**
	 * get the state
	 * @return the state
	 */
	public eFloaterState getState() {
		return state;
	}

	/**
	 * set the state
	 * @param state the state to set
	 */
	public void setState(eFloaterState state) {
		this.state = state;
	}
/**
 * to check if it gets the correct bin or not
 * @return true or false
 */
	public boolean getCorrectBin() {
		
		if (this.throwDir.getDirection() == this.type.getType()) {
			return true;
		}
		else {
			return false;
		}
		
	}

/**
 * Get the bin in the throw direction
 * @return
 */
	public void setBins(Bin recyc, Bin trash) {
		LR.add(recyc);
		LR.add(trash);
		if(LR.get(0).getPosX() > LR.get(1).getPosX()){
			Collections.reverse(LR);
		}
	}
	
	public Bin getBin(){
		//if they throw Left return Left-most bin
		if(throwDir==eThrowDirection.LEFT){
			return LR.get(0);
		}
		//else return right bin
		else{
			return LR.get(1);
		}
	}
	
	public void rest() {
		this.setState(eFloaterState.RESTING);
	}
	
	/**
	 * getter and setter for throw direction
	 * @param e
	 */
	public void setThrowDirection(eThrowDirection e){
		throwDir = e;
	}
	
	public eThrowDirection getThrowDirection(){
		return throwDir;
	}
	

	@Override
	public void catching() {
		this.setState(eFloaterState.LIFTED);
	}

	
}
