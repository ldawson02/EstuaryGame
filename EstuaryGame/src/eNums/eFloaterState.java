package eNums;

public enum eFloaterState {
	MOVING(0), RESTING(1), LIFTED(2), THROWING(3), HITBIN(4);
	
	private int state;
	
	eFloaterState(int estate) {
		state = estate;
	}
	
	public int getState() {
		return state;
	}
}

