package eNums;

public enum eThrowDirection {
	LEFT(0), RIGHT(0);
	
	private int direction;
	
	eThrowDirection(int edir) {
		direction = edir;
	}
	
	public int getDirection() {
		return direction;
	}
}
