package model;

import eNums.eFloaterState;

public class DrawableItem extends Item {
	public int startx;
	public int starty;
	public int finalx;
	public int finaly;
	public int deltaX;
	public int deltaY;
	public int count = 0;
	public int delay = 100;
	public boolean delayOn = true;

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
		finalx = a;
		finaly = b;
		deltaX = finalx-startx;
		deltaY = finaly-starty;
	}
	
	
	public void move(){
		if(delayOn){
			count++;
			if(count > delay){
				delayOn = false;
				count = 0;
			}
			return;
		}
		double distance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		double speed = .025;
		if(Math.abs(deltaX - (this.getPosX()-startx)) > 5){
			this.updatePos((int)(this.getPosX()+deltaX*speed),(int)(this.getPosY()+deltaY*speed));
		}else{
			this.updatePos(startx, starty);
			delayOn = true;
		}

	}
}
