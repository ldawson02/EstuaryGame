package eNums;

public enum eTutorialState {
	DEBRIS(0), EROSION(1), POWERS_REMOVE(2), POWERS_REBUILD(2), HEALTH(3), TIMER(4), DONE(5);
	
	private int state;
	
	eTutorialState(int i){
		this.state = i;
	}
	
	public eTutorialState nextState(){
		eTutorialState state;
		switch (this){
		case DEBRIS:
			state = EROSION;
			break;
		case EROSION:
			state = POWERS_REMOVE;
			break;
		case POWERS_REMOVE:
			state = POWERS_REBUILD;
			break;
		case POWERS_REBUILD:
			state = HEALTH;
			break;
		case HEALTH:
			state = TIMER;
			break;
		default:
			state = DONE;
		}
		return state;	
	}
}
