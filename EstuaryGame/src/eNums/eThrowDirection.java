package eNums;

/**
 * Documents the direction that the player is throwing Debris: Left or Right.
 * This enum also offers the functionality of returning the opposite direction
 * enum.
 * 
 * @author Lia Dawson
 *
 */
public enum eThrowDirection {
	LEFT(1), RIGHT(-1);

	private int direction;

	eThrowDirection(int edir) {
		direction = edir;
	}

	/**
	 * Get the direction
	 * 
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * get the throw direction, if direction equals to negative 1, goes to left,
	 * else goes to right
	 * 
	 * @param dir
	 * @return
	 */
	public static eThrowDirection getDir(int dir) {

		if (dir == -1) {
			return LEFT;
		} else {
			return RIGHT;
		}

	}

	/**
	 * Get the opposite direction of the throw direction
	 * 
	 * @return the opposite direction
	 */
	public eThrowDirection opposite() {
		if (this.direction == -1) {
			return RIGHT;
		} else {
			return LEFT;
		}
	}
}
