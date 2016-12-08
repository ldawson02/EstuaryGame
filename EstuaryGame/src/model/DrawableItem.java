package model;

import eNums.eFloaterState;

public class DrawableItem extends Item {


	/**
	 * Constructor for DrawableItem with specifications
	 * @param x, y, width, height
	 */
	public DrawableItem(int x, int y, int width, int height){
		super(x,y);
		super.setWidth(width);
		super.setHeight(height);
	}
}
