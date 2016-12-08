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
		this.state = eFloaterState.MOVING;
	}
	
	/**
	 * Constructor for Debris at (x, y), and defaulted to MOVING state
	 * @param x, y
	 */
	public Debris(int x, int y){
		super(x,y);
		this.state = eFloaterState.MOVING;
	};
	
	/**
	 * Constructor for Debris defaulted to MOVING, set to type specified
	 * @param etype
	 */
	public Debris(eDebrisType etype) {
		super();
		this.type = etype;
		this.state = eFloaterState.MOVING; // Moving by default on creation
		LR = new ArrayList<Bin>();
	}
	
	/**
	 * getter for the type of the Debris
	 * @return type
	 */
	public eDebrisType getType() {
		return type;
	}

	/**
	 * set the type of the Debris
	 */
	public void setType(eDebrisType type) {
		this.type = type;
	}

	/**
	 * getter for the state of the Debris
	 * @return the state
	 */
	public eFloaterState getState() {
		return state;
	}

	/**
	 * set the state of the Debris
	 */
	public void setState(eFloaterState state) {
		this.state = state;
	}
	
	/**
	 * sets the selected throw direction of the debris
	 */
	public void setThrowDirection(eThrowDirection e){
		throwDir = e;
	}
	
	/**
	 * getter for the selected throw direction of the debris
	 */
	public eThrowDirection getThrowDirection(){
		return throwDir;
	}
	
	/**
	 * sets the state of the Debris to RESTING 
	 */
	public void rest() {
		this.setState(eFloaterState.RESTING);
	}
	
	/**
	 * sets the state of the debris to LIFTED
	 */
	@Override
	public void catching() {
		this.setState(eFloaterState.LIFTED);
	}
	
	/**
	 * to check if the chosen throw direction matches the correct bin (according to its type)
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
	 * sets the bins in the right order
	 */
	public void setBins(Bin recyc, Bin trash) {
		LR.add(recyc);
		LR.add(trash);
		if(LR.get(0).getPosX() > LR.get(1).getPosX()){
			Collections.reverse(LR);
		}
	}
	
	/**
	 * Getter for the bin corresponding to the debris' throw direction
	 * @return
	 */
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
}
