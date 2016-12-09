package eNums;

/**
 * Documents the type of Debris: Trash or Recycling. This enum also keeps track
 * which throw direction the trash and recycling bins correspond to. 
 * 
 * @author Lia Dawson
 *
 */
public enum eDebrisType {
	TRASH(eThrowDirection.RIGHT), RECYCLING(eThrowDirection.LEFT);

	private eThrowDirection binSide;

	/**
	 * Construct a Debris type
	 * 
	 * @param etype
	 */
	eDebrisType(eThrowDirection etype) {
		binSide = etype;
	}

	/**
	 * Gets the direction that the bin of this Debris type is in
	 * @return The direction of the bin
	 */
	public eThrowDirection getBinSide() {
		return binSide;
	}
}
