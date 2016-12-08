package eNums;

/**
 * few floater state 
 *
 */
public enum eFloaterState {
	MOVING(0), RESTING(1), LIFTED(2), THROWING(3), HITBIN(4), INITIATED(5), FROZEN(6);
	
	private int state;
	/**
	 * construct a floater state
	 * @param estate
	 */
	eFloaterState(int estate) {
		state = estate;
	}
	/**
	 * get the floater state
	 * @return state
	 */
	public int getState() {
		return state;
	}
}

