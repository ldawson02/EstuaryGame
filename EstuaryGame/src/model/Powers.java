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
	
	/**
	 * construct powers
	 * @param x
	 * @param y
	 */
	public Powers(int x, int y){
		super(x,y);
	};
	
	public abstract void appear();
	public abstract void disappear();
}
