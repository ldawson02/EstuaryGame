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
	CoastDebrisRemoved(15),
	CoastRebuilt(15);
	
	
	private int delta;
	
	eHealthChanges(int delta) {
		this.delta = delta;
	}
	/**
	 * get the delta
	 * @return delta
	 */
	public int getDelta() {
		return delta;
	}
}
