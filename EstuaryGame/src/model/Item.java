package model;

/**
 * Item is an abstract class that holds the position of an item in x-y coordinates
 * 
 * @author Lia Dawson
 * @version 1.0
 * @since 10/25/16
 */

public abstract class Item {

	private int posX;
	private int posY;
	private int objectID;
	
	/**
	 * Constructor for Item
	 * 
	 * @param x	the initial x position of the Item
	 * @param y	the initial y position of the Item
	 */
	public Item(int x, int y){
		this.posX = x;
		this.posY = y;
	};
	
	/**
	 * Constructor (no args)
	 */
	public Item(){};
	
	/**
	 * Accesses and returns the x position of the Item
	 * 
	 * @return the x position of the Item
	 */
	public int getPosX(){
		return posX;
	};
	
	/**
	 * Accesses and return the y position of the Item
	 * 
	 * @return the y position of the Item
	 */
	public int getPosY(){
		return posY;
	};
	
	/**
	 * Allows for position to be updated
	 * 
	 * @param x the new x position of the Item
	 * @param y the new y position of the item
	 */
	public void updatePos(int x, int y){
		this.posX = x;
		this.posY = y;
	};
	
}
