package eNums;

public enum eDebrisState {
	MOVING(0), RESTING(1), REMOVED(2);
	
	private int state;
	
	eDebrisState(int estate) {
		state = estate;
	}
	
	public int getState() {
		return state;
	}
}
