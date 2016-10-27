package eNums;

public enum eDebrisState {
	MOVING(0), RESTING(1), LIFTED(2), THROWING(3);
	
	private int state;
	
	eDebrisState(int estate) {
		state = estate;
	}
	
	public int getState() {
		return state;
	}
}

