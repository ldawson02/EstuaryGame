package eNums;

public enum eBarrierType {

	Gabion(0), Wall(1), EMPTY(-1);
	
	private int type;
	
	eBarrierType(int val) {
		this.type = val;
	}
	
	public int getType() {
		return type;
	}
	
	public int getDecay(){
		switch(this){
		case Gabion:
			return 12000;
		case Wall:
			return 9000;
		default:
			return 1;
		}
	}
	
}
