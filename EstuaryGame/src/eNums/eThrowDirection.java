package eNums;

/**
 * throw has two direction, left or right
 *
 */
public enum eThrowDirection {
	LEFT(1), RIGHT(-1);
	
	private int direction;
	
	/**
	 * construct a throw direction
	 * @param edir
	 */
	eThrowDirection(int edir) {
		direction = edir;
	}
	/**
	 * get the direction
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}
	/**
	 * get the throw direction, if direction equals to negative 1, goes to left, else goes to right
	 * @param dir
	 * @return
	 */
	public static eThrowDirection getDir(int dir){

		if(dir==-1){
			return LEFT;
		}
		else {
			return RIGHT;
		}

	}
	/**
	 * the oppsite direction of the throw direction
	 * @return
	 */
	public eThrowDirection opposite(){
		if(this.direction==-1){
			return RIGHT;
		}
		else{
			return LEFT;
		}
	}
}
