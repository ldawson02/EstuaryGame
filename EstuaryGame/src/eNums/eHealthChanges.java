package eNums;

/**
 * Documents the changes to the health bar for various game actions. Health
 * Changes include: Debris Hit Coast, Resting Debris, Gradual, Correct Bin,
 * Incorrect Bin, Barrier Fallen, Coast Debris Removed, Rebuilt, and Coast
 * Eroded. The deltas associate with each health change is accessed to change
 * the health bar.
 * 
 * @author Ian Heffner
 */
public enum eHealthChanges {
	DebrisHitCoast(-5), 
	RestingDebrisGradual(-1), 
	CorrectBin(5), 
	IncorrectBin(-5), 
	BarrierFallen(-2), 
	CoastDebrisRemoved(15), 
	Rebuilt(15), 
	CoastEroded(-3);

	private int delta;

	eHealthChanges(int delta) {
		this.delta = delta;
	}

	/**
	 * Get the delta
	 * 
	 * @return delta
	 */
	public int getDelta() {
		return delta;
	}
}
