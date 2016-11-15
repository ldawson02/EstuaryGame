package eNums;

public enum eBarrierType {

	Empty(-1), Gabion(0), Wall(1);
	
	private int type;
	
	eBarrierType(int val) {
		this.type = val;
	}
	
	public int getType() {
		return type;
	}
	
}
