package eNums;

/**
 * this class is to write the player state, there are three states for player, idle, moving, and lifting
 * @author Pu
 *
 */
public enum ePlayerState {
	Idle(0),
	Moving(1),
	Lifting(2);
	
	private int state;
	/**
	 * construct a player state
	 * @param val
	 */
	ePlayerState(int val) {
		state = val;
	}
	/**
	 * get the state val
	 * @return state
	 */
	public int getStateVal() {
		return state;
	}
	
}
