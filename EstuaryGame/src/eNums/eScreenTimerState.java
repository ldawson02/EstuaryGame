package eNums;

public enum eScreenTimerState {
	ON(0), OFF(0), FROZEN(0);
	
	private int state;
	
	eScreenTimerState(int estate) {
		state = estate;
	}
	
	public int getState() {
		return state;
	}
}
