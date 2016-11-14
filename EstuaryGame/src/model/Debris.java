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
	public void setController(GameController game){
		this.gc = game;
	}
	
	/**
	 * @return the type
	 */
	public eDebrisType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(eDebrisType type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	public eFloaterState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(eFloaterState state) {
		this.state = state;
	}

	public boolean getCorrectBin() {
		return this.correctBin;
	}
	
	public void setCorrectBin(eDebrisType correctBin) {
		if (getType() == correctBin) 
			this.correctBin = true;
		else 
			this.correctBin = false;
	}

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
