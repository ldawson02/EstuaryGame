package model;

public class Storm extends Item {
	
	/**
	* Storm class- has appear(), destroyGabions(), and addTrash() methods
	* 
	* @author Esme Li
	* @version 1.0
	* @since 10/25/16
	*/
	
	private static int numDamage = 10;
	private static int numTrash;
	private int lifetime;
	
	/**
	 * Constructs a Storm at 0,0
	 */
	
	public Storm() {
		super(0, 0);  //change later
	}
	
	public void setLifetime(int x) {
		this.lifetime = x;
	}
	
	/**
	 * Makes the storm in the game at x, y 
	 */
	public void appear() {
		
	};
	
	/**
	 * Destroys the gabions at the set damage, numDamage
	 */
	public void destroyGabions() {
		
	};
	
	
	/**
	 * Adds a # of trash, numTrash
	 */
	public void addTrash() {
		
	};
}
