package model;

import eNums.eDebrisType;
/**
 * 
 * the Bin has a trashType
 *
 */
public class Bin extends Item{

	public eDebrisType trashType;
	
	private Bin(){};
	
	/**
	 * the constructor of the bin
	 * @param t
	 */
	public Bin(eDebrisType t){
		this.trashType = t;
		this.setHeight(50);
		this.setWidth(50);
	}
	
	/**
	 * getter for debris type
	 * @return trashtype;
	 */
	public eDebrisType getDebrisType(){
		return trashType;
	}
	
	public void correctBinAction(){};
	
	public void wrongBinAction(){};
}
