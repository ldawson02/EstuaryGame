package eNums;

/**
 * this class write for the screen timer state, there are three states listed 
 *
 */
public enum eScreenTimerState {
	ON(0), OFF(0), FROZEN(0);
	
	private int state;
	/**
	 * construct a screen timer state
	 * @param estate
	 */
	eScreenTimerState(int estate) {
		state = estate;
	}
	/**
	 * get the state
	 * @return state
	 */
	public int getState() {
		return state;
	}
}
