package model;

public abstract class Item {
	/**
	 * 
	 */
	private int posX;
	private int posY;
	private int objectID;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Item(int x, int y){
		this.posX = x;
		this.posY = y;
	};
	
	public int getPosX(){
		return posX;
	};
	public int getPosY(){
		return posY;
	};
	
	public abstract void updatePos(int x, int y);
	
}
