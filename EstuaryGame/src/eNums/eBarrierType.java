package eNums;

/**
 * This documents the type of Barrier: Gabion, Wall, or Empty
 *
 */
public enum eBarrierType {

	Gabion(0), Wall(1), EMPTY(-1);
	
	private int type;
	
	eBarrierType(int val) {
		this.type = val;
	}
	
	/**
	 * Get the barrier type
	 * @return type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Get the decay for barrier type
	 * @return decay speed
	 */
	public int getDecay(){
		switch(this){
		case Gabion:
			return 26000;
		case Wall:
			return 13000;
		default:
			return 1000000;
		}
	}
	
}
