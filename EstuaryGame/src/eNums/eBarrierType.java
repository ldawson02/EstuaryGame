package eNums;

public enum eBarrierType {

	Gabion(0), Wall(1);
	
	private int type;
	
	eBarrierType(int val) {
		this.type = val;
	}
	
	public int getType() {
		return type;
	}
	
}
