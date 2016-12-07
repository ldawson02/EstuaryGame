package model;

import eNums.eDebrisType;
/**
 * 
 * the Bin has a trashType
 *
 */
public class Bin extends Item{

	public eDebrisType debrisType;
	
	public static final int defaultHeight = 50;
	public static final int defaultWidth = 50;
	
	private Bin(){};
	
	/**
	 * the constructor of the bin
	 * @param t
	 */
	public Bin(eDebrisType t){
		this.debrisType = t;
		this.setHeight(defaultHeight);
		this.setWidth(defaultWidth);
	}
	
	/**
	 * getter for debris type
	 * @return debristype;
	 */
	public eDebrisType getDebrisType(){
		return debrisType;
	}
	
}
