package controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import controller.*;
import eNums.eBarrierType;
import model.*;

/**
 * the collision has a mainplaer
 * 
 *
 */
public class Collisions implements Serializable {
	private Player mainPlayer;
	
	/**
	 * set the player
	 * @param p
	 */
	public void setPlayer(Player p){
		mainPlayer = p;
	}
	/**
	 * check the collison
	 * @param i
	 * @return
	 */
	public boolean checkCollision(Item i){
		Rectangle r1 = new Rectangle(i.getPosX(), i.getPosY(), i.getWidth(), i.getHeight());
		Rectangle r2 = new Rectangle(mainPlayer.getPosX(), mainPlayer.getPosY(), mainPlayer.getWidth(), mainPlayer.getHeight());
		return r1.intersects(r2);
	}
	
	/**
	 * when item i1 and item i2 interacts, collection happens.
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static boolean checkCollision(Item i1, Item i2){		
		
		int i1L = i1.getPosX();
		int i1R = i1L + i1.getWidth();
		int i1T = i1.getPosY();
		int i1B = i1T + i1.getHeight();
		
		int i2X = i2.getPosX() + i2.getWidth()/2;
		int i2Y = i2.getPosY() + i2.getHeight()/2;
		
		if((i2X>i1L && i2X<i1R) && (i2Y>i1T && i2Y<i1B)){
			//then its a collision!!
			return true;
		}
		else{
			return false;
		}
		
		/*
		Rectangle r1 = new Rectangle(i1.getPosX(), i1.getPosY(), i1.getWidth(), i1.getHeight());
		Rectangle r2 = new Rectangle(i2.getPosX(), i2.getPosY(), i2.getWidth(), i2.getHeight());
		return r1.intersects(r2);
		*/

	}
	/**
	 * convert the item into rectangle
	 * @param i
	 * @param p
	 * @return
	 */
	public static boolean pointInside(Item i, Point p) {
		//convert item into rectangle
		//System.out.println("point inside");
		Rectangle r = new Rectangle(i.getPosX(), i.getPosY(), i.getWidth(), i.getHeight());
		if (r.contains(p)) {  //if p is within bounds of r (the item)
			return true;
		}
		else {
			return false;
		}
	}
}
