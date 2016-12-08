package model;

import eNums.eFloaterState;

public class DrawableItem extends Item {
	public int startx;
	public int starty;
	public int finalx;
	public int finaly;
	public int deltaX;
	public int deltaY;

	/**
	 * Constructor for DrawableItem with specifications
	 * @param x, y, width, height
	 */
	public DrawableItem(int x, int y, int width, int height){
		super(x,y);
		super.setWidth(width);
		super.setHeight(height);
	}
	
	public void setStartandEnd(int x, int y, int a, int b){
		startx = x;
		starty = y;
		finalx = x;
		finaly = y;
		deltaX = finalx-startx;
		deltaY = finaly-starty;
	}
	
	
	public void move(){
		double distance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		double speed = 0.25;
		if(this.getPosX()-startx > 0){
			this.updatePos((int)(this.getPosX()+deltaX*speed),(int)(this.getPosY()+deltaY*speed));
		}else{
			this.updatePos(startx, starty);
		}

	}
}
