package model;

import java.io.Serializable;
import java.util.ArrayList;

import controller.ActiveItems;
import controller.GameController;

/**
 * 
 * 
 * @author Rachel Muzzelo
 * @version 1.0
 * @since 10/26/16
 */

public abstract class Powers extends Floater implements Serializable{
	
	public Powers(int x, int y){
		super(x,y);
	};
	public Powers(){
		super();
	}
	
	public abstract void power(ActiveItems items);
}
