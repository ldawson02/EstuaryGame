package eNums;

public enum eDebrisType {
	TRASH(0), RECYCLING(1);
	
	private int type;
	
	eDebrisType(int etype) {
		type = etype;
	}
	
	public int getType() {
		return type;
	}
	
}
