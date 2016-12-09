package eNums;

/**
 * Documents the state of the Screen Timer: On, Off, or Frozen. This state is
 * used to determine how to add time to the timer.
 * 
 * @author Ian Heffner
 *
 */
public enum eScreenTimerState {
	ON(0), OFF(0), FROZEN(0);

	private int state;

	/**
	 * Construct a screen timer state
	 * 
	 * @param estate
	 */
	eScreenTimerState(int estate) {
		state = estate;
	}

}
