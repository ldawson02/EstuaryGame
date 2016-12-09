package eNums;

/**
 * Documents the player's state: Idle, Moving, or Lifting
 * @author Ian Heffner
 *
 */
public enum ePlayerState {
	Idle(0),
	Moving(1),
	Lifting(2);
	
	private int state;
	/**
	 * Construct a player state
	 * @param val
	 */
	ePlayerState(int val) {
		state = val;
	}
	/**
	 * Get the state
	 * @return state
	 */
	public int getStateVal() {
		return state;
	}
	
}
