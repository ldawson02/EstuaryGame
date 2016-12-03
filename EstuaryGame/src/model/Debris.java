package model;

import eNums.eFloaterState;

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

public class Debris extends Floater{

	private eDebrisType type;
	private eFloaterState state;
	private boolean correctBin;
	private eThrowDirection throwDir = eThrowDirection.LEFT;
	private GameController gc;
	
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
	}
	
	//This is only connected when the Debris has been caught
	/**
	 * set the controller
	 * @param game
	 */
	public void setController(GameController game){
		this.gc = game;
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
		/**
		 * I don't understand how whether it was the correct bin was working before
		 * so I'm rewriting it here
		 */
		
		if (this.throwDir.getDirection() == this.type.getType()) {
			return true;
		}
		else {
			return false;
		}
		
		//return this.correctBin;
	}
	/**
	 * set the correct bin
	 * @param correctBin
	 */
	public void setCorrectBin(eDebrisType correctBin) {
		if (getType() == correctBin) 
			this.correctBin = true;
		else 
			this.correctBin = false;
	}
/**
 * getter for bin,Put recycle in first, then trash,if they throw Left return Left-most bin,else return right bin
 * @return
 */
	public Bin getBin(){
		ArrayList<Bin> LR = new ArrayList<Bin>();
		//Put recycle in first, then trash
		LR.add(gc.getItems().getRecycleBin());
		LR.add(gc.getItems().getTrashBin());
		
		if(LR.get(0).getPosX() > LR.get(1).getPosX()){
			Collections.reverse(LR);
		}

		
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
	
	/**
	 * Throws debris that is trash to the left and recyclable debris to the right
	 * @return
	 */
	
	public void throwDebris(boolean cBin) {
		correctBin = cBin;
		if(getType() == eDebrisType.TRASH){
			setThrowDirection(eThrowDirection.LEFT);
		}else{
			setThrowDirection(eThrowDirection.RIGHT);
		}
		
		if(!correctBin){
			setThrowDirection(this.getThrowDirection().opposite());
		}
		
		this.setState(eFloaterState.THROWING);
	}
	

	public void wrongBinAction(){
		
	}

	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catching() {
		this.setState(eFloaterState.LIFTED);
	}

	
}
