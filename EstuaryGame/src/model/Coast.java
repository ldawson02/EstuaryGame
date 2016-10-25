package model;

/**
* The Coast class is the Model class for the coast item that is in the game.
* 
* @author Ian Heffner
* @version 1.0
* @since 10/25/16
*/

public class Coast extends Item {

	/**
	 * The current size of the coast
	 */
	private int size;
	/**
	 * The minimum size of the coast
	 */
	private int minsize;
	/**
	 * The maximum size of the coast
	 */
	private int maxsize;
	/**
	 * The rate at which it erodes
	 */
	private double erosionRate;

	/**
	 * Constructs a Coast with position (x, y)
	 * @param x
	 * @param y
	 */
	public Coast(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Decreases the size of the coast by one, if possible
	 */
	public void erode() {
		
	}
	
	/**
	 * Increases the size of the coast by one, if possible
	 */
	public void rebuild() {
		
	}
	
	/**
	 * Changes the erosion rate by the specified amount
	 * @param amount
	 */
	public void changeErosionRate(double amount) {
		
	}
	
}
