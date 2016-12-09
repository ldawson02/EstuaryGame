package eNums;
/**
 * Documents the type of Debris: Trash or Recycling
 *
 */
public enum eDebrisType {
	TRASH(-1), RECYCLING(1);
	
	private int type;
	
	/**
	 * Construct a edebris type
	 * @param etype
	 */
	eDebrisType(int etype) {
		type = etype;
	}
	/**
	 * Get the type
	 * @return type
	 */
	public int getType() {
		return type;
	}
	
}
