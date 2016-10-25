package model;

import java.util.ArrayList;

/**
 * This is the AllDebris class in the Model. The AllDebris class
 * is contained by the Coast and keeps track of Debris that has come to
 * rest on the coast, thereby able to damage the coast's health.
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 10/25/16
 */
public class AllDebris {
	
	/**
	 * List of debris items
	 */
	public ArrayList<Debris> debris;
	/**
	 * Current number of debris items
	 */
	private int count;
	
	/**
	 * Constructor
	 */
	public AllDebris() {
		debris = new ArrayList<Debris>();
	}
	
	/**
	 * Find a specific debris item, d
	 * @param d
	 * @return boolean whether or not it is found
	 */
	public boolean find(Debris d) {
		return true; //remove this
	}
	
	/**
	 * Add a Debris item to the list
	 * @param d
	 */
	public void addItem(Debris d) {
		
	}
	
	/**
	 * Remove a specific Debris item
	 * @param d
	 * @return the item
	 */
	public Debris removeItem(Debris d) {
		return new Debris(); //remove this
	}
	
	/**
	 * @return the current count
	 */
	public int count() {
		return count; //remove this
	}
	
	/**
	 * Empty the list
	 */
	public void clearAll(){
		count = 0;
	}
}
