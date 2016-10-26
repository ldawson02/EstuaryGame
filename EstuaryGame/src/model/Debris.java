package model;

import eNums.eDebrisType;

/**
 * 
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 10/25/16
 */

public class Debris extends Item implements Floater {

	public eDebrisType type;
	public int speed;
	
	/**
	 * Private no-arg constructor to prevent creating a debris item without
	 * a type (trash vs recycling)
	 */
	private Debris() {
	}
	
	public Debris(eDebrisType etype) {
		this.type = etype;
	}
	
	public void rest() {
		
	}
	
	public void throwRecycling(){
		
	}
	
	public void throwTrash(){
		
	}
	
	public boolean correctBin(){
		return false;
	}
	
	public void wrongBinAction(){
		
	}
}
