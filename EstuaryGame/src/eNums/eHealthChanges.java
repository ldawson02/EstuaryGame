package eNums;

/**
 * Keeping all the health changes to a set of constants so that balancing the game
 * is easier later.
 * 
 * @author Ian
 */
public enum eHealthChanges {
	DebrisHitCoast(-5),
	RestingDebrisGradual(-1),
	CorrectBin(5),
	IncorrectBin(-5),
	WallFallen(-8),
	WallBuilt(12),
	CoastDebrisRemoved(10);
	
	
	private int delta;
	
	eHealthChanges(int delta) {
		this.delta = delta;
	}
	
	public int getDelta() {
		return delta;
	}
}
