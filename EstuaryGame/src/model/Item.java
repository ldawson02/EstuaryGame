package model;

import java.io.Serializable;

/**
 * Item is an abstract class that holds the position of an item in x-y coordinates
 * 
 * @author Lia Dawson
 * @version 1.0
 * @since 10/25/16
 */
/**
 * The itme should have a x position, y position, width and height
 * @author Pu
 *
 */
public abstract class Item implements Serializable{

	private int posX;
	private int posY;
	private int width;
	private int height;
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
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

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
	
	public void updatePosX(int x){
		this.posX = x;
	}
	
	public void updatePosY(int y){
		this.posY = y;
	}
	
}
