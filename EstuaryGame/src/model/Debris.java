package model;

import eNums.eDebrisState;
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
	private eDebrisState state;
	
	
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
		this.state = eDebrisState.MOVING; // Moving by default on creation
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
	public eDebrisState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(eDebrisState state) {
		this.state = state;
	}



	public void rest() {
		
	}
	
	public void throwDebris(eThrowDirection dir) {
		
	}
	
	public boolean correctBin(eThrowDirection dir){
		return false;
	}
	
	public void wrongBinAction(){
		
	}

	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catching() {
		// TODO Auto-generated method stub
		
	}

	
}
