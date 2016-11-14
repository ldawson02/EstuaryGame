package eNums;

/**
 * Keeping all the health changes to a set of constants so that balancing the game
 * is easier later.
 * 
 * @author Ian
 */
public enum eHealthChanges {
	DebrisOnCoast(-10),
	DebrisThrown(5),
	WallFallen(-10),
	WallBuilt(15);
	
	private int delta;
	
	eHealthChanges(int delta) {
		this.delta = delta;
	}
	
	public int getDelta() {
		return delta;
	}
}
