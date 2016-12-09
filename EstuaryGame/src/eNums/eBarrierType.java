package eNums;

/**
 * Documents the type of Barrier: Gabion, Wall, or Empty. This enum also holds
 * the decay value of each respective barrier type.
 * 
 * @author Lia Dawson
 *
 */
public enum eBarrierType {

	Gabion(26000), Wall(13000), EMPTY(1000000);

	private int decay;

	eBarrierType(int val) {
		this.decay = val;
	}

	/**
	 * Get the decay for barrier type
	 * 
	 * @return decay speed
	 */
	public int getDecay() {
		return decay;
	}

}
