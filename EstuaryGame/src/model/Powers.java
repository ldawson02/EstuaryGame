package model;

/**
 * 
 * 
 * @author Rachel Muzzelo
 * @version 1.0
 * @since 10/26/16
 */

public abstract class Powers extends Item implements Interactable, Floater {
	public Powers(int x, int y){
		super(x,y);
	};
	public abstract void appear();
	public abstract void disappear();
}
