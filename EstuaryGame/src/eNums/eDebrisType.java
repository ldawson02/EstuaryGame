package eNums;
/**
 * there are two kinds of deris, one is trash, one is recycling
 *
 */
public enum eDebrisType {
	TRASH(-1), RECYCLING(1);
	
	private int type;
	
	/**
	 * construct a edebris type
	 * @param etype
	 */
	eDebrisType(int etype) {
		type = etype;
	}
	/**
	 * get the type
	 * @return type
	 */
	public int getType() {
		return type;
	}
	
}
