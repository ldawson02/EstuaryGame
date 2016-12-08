package eNums;

/**
 * the barrier has gabion, wall or empty.
 *
 */
public enum eBarrierType {

	Gabion(0), Wall(1), EMPTY(-1);
	
	private int type;
	
	/**
	 * constract a ebarrier type
	 */
	eBarrierType(int val) {
		this.type = val;
	}
	/**
	 * get the barrier type
	 * @return type
	 */
	public int getType() {
		return type;
	}
	/**
	 * get the decay for barrier
	 * @return decay speed
	 */
	public int getDecay(){
		switch(this){
		case Gabion:
			return 17000;
		case Wall:
			return 12000;
		default:
			return 1000000;
		}
	}
	
}
