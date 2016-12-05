package eNums;

public enum eTutorialState {
	DEBRIS(0), EROSION(1), POWERS(2), HEALTH(3), TIMER(4), DONE(5);
	
	private int state;
	
	eTutorialState(int i){
		this.state = i;
	}
	
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
