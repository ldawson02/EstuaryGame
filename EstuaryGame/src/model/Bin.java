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
	
	public static final int defaultHeight = 50;
	public static final int defaultWidth = 50;
	
	private Bin(){};
	
	/**
	 * the constructor of bin, set to the default height and width and to eDebrisType t
	 * @param eDebrisType
	 */
	public Bin(eDebrisType t){
		this.debrisType = t;
		this.setHeight(defaultHeight);
		this.setWidth(defaultWidth);
	}
	
	/**
	 * getter for the bin's type
	 * @return debrisType;
	 */
	public eDebrisType getDebrisType(){
		return debrisType;
	}
	
}
