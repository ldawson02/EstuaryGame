package model;

import java.io.Serializable;

import eNums.eDebrisType;
/**
 * The Bin class represents the two types of selections a debris should be able to be thrown into.
 * It has a specific debrisType assigned to it and a height and width. 
 */
public class Bin extends Item implements Serializable{

	public eDebrisType debrisType;
	
	public static final int defaultHeight = 50;
	public static final int defaultWidth = 50;
	
	private Bin(){};
	
	/**
	 * The constructor of bin, set to the default height and width and with debris type
	 * @param t debris type of bin
	 */
	public Bin(eDebrisType t){
		this.debrisType = t;
		this.setHeight(defaultHeight);
		this.setWidth(defaultWidth);
	}
	
	/**
	 * Getter for the bin's type
	 * @return debrisType;
	 */
	public eDebrisType getDebrisType(){
		return debrisType;
	}
	
}
