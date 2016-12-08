package eNums;

/**
 * this class is to write the helper's state, the helper can walk,pick up trash, hold, walk off, and void
 * @author megan
 *
 */
public enum eHelperState {
	
	WALKING(0), PICKING_UP(1), HOLDING(2), WALKING_OFF(3), VOID(4);
	
	eHelperState(int i){}
	/**
	 * get the time limit, if pick up is 1000, hold is 1500, and walk.
	 * @return
	 */
	public int getTimeLimit(){
		switch(this){
		case PICKING_UP:
			return 1000;
		case HOLDING:
			return 1500;
		case WALKING:
		default:
			return 100000;
		}
	}
	/**
	 * write the helper's next state, if walk, then pick up the trash, the hold it, walk off 
	 * @return
	 */
	public eHelperState nextState(){
		switch(this){
		case WALKING:
			return PICKING_UP;
		case PICKING_UP:
			return HOLDING;
		case HOLDING:
			return WALKING_OFF;
		case WALKING_OFF:
		default:
			return VOID;
		}
	}
}
