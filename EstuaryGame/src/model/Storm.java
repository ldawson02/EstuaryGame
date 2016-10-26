package model;

public class Storm extends Item {
	
	/**
	* Storm class- has appear(), destroyGabions(), and addTrash() methods
	* 
	* @author Esme Li
	* @version 1.0
	* @since 10/25/16
	*/
	
	/**
	 * Constructs a Storm 
	 */
	
	private static int numDamage = 10;
	private static int numTrash;
	private int lifetime;
	private int x = 0;
	private int y = 0;
	
	public Storm() {
		super();
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
