package model;

import java.io.Serializable;

import eNums.eDebrisType;
/**
 * 
 * the Bin has a trashType
 *
 */
public class Bin extends Item implements Serializable{

	public eDebrisType debrisType;
	
	private Bin(){};
	
	/**
	 * the constructor of the bin
	 * @param t
	 */
	public Bin(eDebrisType t){
		this.debrisType = t;
		this.setHeight(50);
		this.setWidth(50);
	}
	
	/**
	 * getter for debris type
	 * @return debristype;
	 */
	public eDebrisType getDebrisType(){
		return debrisType;
	}
	
}
