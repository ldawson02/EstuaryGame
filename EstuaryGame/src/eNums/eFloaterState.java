package eNums;

/**
 * Documents the different states a Floater can be in: Moving, Resting, Lifted,
 * Throwing, Hitbin, Initiated, or Frozen. These enums are checked to determine
 * how to move a floater.
 *
 */
public enum eFloaterState {
	MOVING(0), RESTING(1), LIFTED(2), THROWING(3), HITBIN(4), INITIATED(5), FROZEN(6);

	private int state;

	/**
	 * Construct a floater state
	 * 
	 * @param estate
	 */
	eFloaterState(int estate) {
		state = estate;
	}

	/**
	 * Get the floater state
	 * 
	 * @return state
	 */
	public int getState() {
		return state;
	}
}
