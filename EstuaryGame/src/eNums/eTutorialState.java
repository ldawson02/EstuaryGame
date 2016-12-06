package eNums;
/**
 * the tutorial state class has six states listed below
 */
public enum eTutorialState {
	DEBRIS(0), EROSION(1), POWERS(2), HEALTH(3), TIMER(4), DONE(5);
	
	private int state;
	/**
	 * construct the state
	 * @param i
	 */
	eTutorialState(int i){
		this.state = i;
	}
	
	/**
	 * the next state for the tutorial, if it is debris, then will be the erosion, and the next state is powers,
	 * and then health, timer 
	 * @return state
	 */
	public eTutorialState nextState(){
		eTutorialState state;
		switch (this){
		case DEBRIS:
			state = EROSION;
		case EROSION:
			state = POWERS;
		case POWERS:
			state = HEALTH;
		case HEALTH:
			state = TIMER;
		default:
			state = DONE;
		}
		return state;	
	}
}