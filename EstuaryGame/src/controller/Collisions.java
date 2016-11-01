package controller;

import controller.*;
import model.*;

public class Collisions {
	private Player mainPlayer;
	
	public void setPlayer(Player p){
		mainPlayer = p;
	}
	
	public boolean checkCollision(Item i){
		int playerL = mainPlayer.getPosX();
		int playerR = playerL + mainPlayer.getWidth();
		int playerT = mainPlayer.getPosY();
		int playerB = playerT + mainPlayer.getHeight();
		
		int itemX = i.getPosX() + i.getWidth()/2;
		int itemY = i.getPosY() + i.getHeight()/2;
		
		if((itemX>playerL && itemX<playerR) && (itemY>playerT && itemY<playerB)){
			//then its a collision!!
			return true;
		}
		else{
			return false;
		}
	}
}
