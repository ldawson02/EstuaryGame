package eNums;

public enum ePlayerState {
	Idle(0),
	Moving(1),
	Lifting(2);
	
	private int state;
	
	ePlayerState(int val) {
		state = val;
	}
	
	public int getStateVal() {
		return state;
	}
	
}
