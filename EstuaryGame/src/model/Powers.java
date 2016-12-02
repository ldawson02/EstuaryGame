package model;

import controller.GameController;

/**
 * 
 * 
 * @author Rachel Muzzelo
 * @version 1.0
 * @since 10/26/16
 */

public abstract class Powers extends Floater implements Interactable {
	private static int pointsMade = 25;
	
	public Powers(int x, int y){
		super(x,y);
	};
	
	public static int getPointsMade() {
		return pointsMade;
	}
	
	public abstract void appear();
	public abstract void disappear();
}
