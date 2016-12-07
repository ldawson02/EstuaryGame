package eNums;

public enum eTutorialState {
	DEBRIS(0), EROSION_GABION(1), EROSION_WALL(1), POWERS_REMOVE(2), POWERS_REBUILD(2), HEALTH(3), TIMER(4), DONE(5), IDLE(-1);
	
	private int state;
	
	eTutorialState(int i){
		this.state = i;
	}
	
	public eTutorialState nextState(){
		eTutorialState state;
		switch (this){
		case DEBRIS:
			state = EROSION_GABION;
			break;
		case EROSION_GABION:
			state = EROSION_WALL;
			break;
		case EROSION_WALL:
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
