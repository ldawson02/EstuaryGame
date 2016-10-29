package IanCollisions;

public enum eShapeState {
	FREE(0), REST(1), TOUCHED(2);
	
	int state;
	
	eShapeState(int estate) {
		state = estate;
	}
	
	public int getState() {
		return state;
	}
	
}
