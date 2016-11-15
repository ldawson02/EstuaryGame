package controller;

import controller.*;
import model.*;

public class Collisions {
	private Player mainPlayer;
	
	public void setPlayer(Player p){
		mainPlayer = p;
	}
	
	public boolean checkCollision(Item i){
		return checkCollision(mainPlayer, i);
	}
	
	public boolean checkCollision(Item i1, Item i2){
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
	}
}
