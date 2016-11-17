package eNums;

public enum eCoastState {
	NO_HIT(0), ONE_HIT(1), TWO_HIT(2), THREE_HIT(3), ERODED(4);
	
	private int hits;

	eCoastState(int i){}
	
	public int getHits(){
		return hits;
	}
	public eCoastState getNextState(){
		eCoastState state;
		switch(this){
		case NO_HIT:
			state = ONE_HIT;
			break;
		case ONE_HIT:
			state = TWO_HIT;
			break;
		case TWO_HIT:
			state = THREE_HIT;
			break;
		case THREE_HIT:
			state = ERODED;
		default:
			state = ERODED;
		}
		return state;
	}
}